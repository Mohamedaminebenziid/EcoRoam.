<?php

namespace App\Controller;

use App\Entity\Course;
use App\Form\CourseType;
use App\Repository\CourseRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCode\ErrorCorrectionLevel;
use Endroid\QrCode\Label\Label;
use Endroid\QrCode\QrCode;

#[Route('/course')]
class CourseController extends AbstractController
{
    #[Route('/', name: 'app_course_index', methods: ['GET'])]
    public function index(CourseRepository $courseRepository): Response
    {
        return $this->render('course/index.html.twig', [
            'courses' => $courseRepository->findAll(),
            
        ]);
    }
    #[Route('/courses', name: 'app_course_indextemp', methods: ['GET'])]

    public function showCours(EntityManagerInterface $entityManager)
    {
        // Récupérer tous les articles
        $cours = $entityManager->getRepository(Course::class)->findAll();

    
        // Créer le rendu Twig
        return $this->render('course/courses.html.twig', [
            'course' => $cours,
        ]);
    }

    #[Route('/new', name: 'app_course_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,SluggerInterface $slugger): Response
    {
        $course = new Course();
        $form = $this->createForm(CourseType::class, $course);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $image = $form->get('image')->getData();

            // this condition is needed because the 'brochure' field is not required
            // so the PDF file must be processed only when a file is uploaded
            if ($image) {
                $originalFilename = pathinfo($image->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$image->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $image->move(
                        $this->getParameter('course_img'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $course->setImage($newFilename);
            }
            $entityManager->persist($course);
            $entityManager->flush();

            return $this->redirectToRoute('app_course_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('course/new.html.twig', [
            'course' => $course,
            'courseform' => $form,
        ]);
    }
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    #[Route('/{id}', name: 'app_course_show', methods: ['GET'])]
    public function show(Course $course): Response
    {

        return $this->render('course/show.html.twig', [
            'course' => $course,
           
        ]);
    }
    #[Route('/details/{id}', name: 'app_course_showdetails', methods: ['GET'])]
    public function showdetails(Course $course): Response
    {
        return $this->render('course/coursedetails.html.twig', [
            'course' => $course,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_course_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Course $course, SluggerInterface $slugger,$id ,EntityManagerInterface $entityManager): Response
    {
        $course = $this->entityManager->getRepository(Course::class)->find($id);
        $form = $this->createForm(CourseType::class, $course);

        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            $image = $request->files->get('course')['image'];
            $uploadsDirectory = $this->getParameter('course_img');
            $filename = md5(uniqid()) . '.' . $image->guessExtension();
            $image->move(
                $uploadsDirectory,
                $filename
            );
        
            $course->setImage($filename);
            $entityManager->flush();
        
            return $this->redirectToRoute('app_course_index');
        }
        
        return $this->render('course/edit.html.twig', [
            'course' => "course",
            'courseform' => $form->createView(),
        ]);
    }

    #[Route('/{id}', name: 'app_course_delete', methods: ['POST'])]
    public function delete(Request $request, Course $course, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$course->getId(), $request->request->get('_token'))) {
            $entityManager->remove($course);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_course_index', [], Response::HTTP_SEE_OTHER);
        
    }
 /*   #[Route('/generate-qr-code/{courseId}', name: 'generate_qr_code')]
public function generateQrCode(int $courseId, BuilderInterface $builder): Response
{
    // Generate the URL to be encoded in the QR code
    $url = $this->generateUrl('app_course_showdetails', ['id' => $courseId], true);

    // Create a QR code instance
    $qrCode = QrCode::create($url)
        ->setSize(300) // Set the size of the QR code
        ->setMargin(10) // Set the margin around the QR code
        ->setErrorCorrectionLevel(ErrorCorrectionLevel::HIGH); // Set error correction level

    // Add a label to the QR code (optional)
    $qrCode->setLabel('Scan to access the course', 16, null, Label::ALIGN_CENTER);

    // Save the QR code to a file (optional)
    $qrCode->writeFile('course/courses/' . $courseId . '_qr_code.png');

    // Alternatively, render the QR code directly in the response
    return new Response($qrCode->writeString(), Response::HTTP_OK, ['Content-Type' => 'image/png']);
}
*/
}
