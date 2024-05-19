<?php

namespace App\Controller;
use App\Entity\Course;
use App\Entity\Certificate;
use App\Form\CertificateType;
use App\Repository\CertificateRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


#[Route('/certificate')]
class CertificateController extends AbstractController
{
    #[Route('/', name: 'app_certificate_index', methods: ['GET'])]
    public function index(CertificateRepository $certificateRepository): Response
    {
        return $this->render('certificate/index.html.twig', [
            'certificates' => $certificateRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_certificate_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $certificate = new Certificate();
        $form = $this->createForm(CertificateType::class, $certificate);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($certificate);
            $entityManager->flush();

            return $this->redirectToRoute('app_certificate_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('certificate/new.html.twig', [
            'certificate' => $certificate,
            'certificateform' => $form,
        ]);
    }
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    #[Route('/certificate/create/{courseId}', name: 'app_certificate_create')]
    public function create(Request $request, $courseId): Response
    {
        // Récupérer le cours associé à l'ID fourni
        $course = $this->getDoctrine()->getRepository(Course::class)->find($courseId);

        // Vérifier si le cours existe
        if (!$course) {
            throw $this->createNotFoundException('Le cours avec l\'ID ' . $courseId . ' n\'existe pas.');
        }

        // Créer une nouvelle instance de certificat et l'associer au cours
        $certificate = new Certificate();
        $certificate->setCours($course);

        // Créer le formulaire
        $form = $this->createForm(CertificateType::class, $certificate);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Gérer la soumission du formulaire
            //$certificate->setCTitle($course.title);

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($certificate);
            $entityManager->flush();

            // Rediriger l'utilisateur vers une autre page après la création du certificat
            return $this->redirectToRoute('app_course_indextemp');
        }

        // Rendre le formulaire avec le cours associé pour l'affichage dans le template
        return $this->render('certificate/new.html.twig', [
            'certificateform' => $form->createView(),
            'course' => $course
        ]);
    }

    #[Route('/{id}', name: 'app_certificate_show', methods: ['GET'])]
    public function show(Certificate $certificate): Response
    {
        return $this->render('certificate/show.html.twig', [
            'certificate' => $certificate,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_certificate_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Certificate $certificate, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CertificateType::class, $certificate);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_certificate_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('certificate/edit.html.twig', [
            'certificate' => $certificate,
            'certificateform' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_certificate_delete', methods: ['POST'])]
    public function delete(Request $request, Certificate $certificate, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$certificate->getId(), $request->request->get('_token'))) {
            $entityManager->remove($certificate);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_certificate_index', [], Response::HTTP_SEE_OTHER);
    }
}
