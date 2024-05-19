<?php

namespace App\Controller;

use App\Entity\Destination;
use App\Entity\Propertysearch;
use App\Form\DestinationType;
use App\Form\PropertysearchType;
use App\Repository\DestinationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;


#[Route('/destination')]
class DestinationController extends AbstractController
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    #[Route('/', name: 'app_destination_index', methods: ['GET'])]
    public function index(DestinationRepository $destinationRepository): Response
    {
        return $this->render('destination/index.html.twig', [
            'destinations' => $destinationRepository->findAll(),
        ]);
    }
    #[Route('/destinations', name: 'app_destination_indextemp', methods: ['GET'])]

    public function showDest(EntityManagerInterface $entityManager, PaginatorInterface $paginator, Request $request)
    {
        // Récupérer tous les articles
        $destQuery = $entityManager->getRepository(Destination::class)->findAll(); // Get all destinations query
        
        $dest = $paginator->paginate(
            $destQuery, // Query to paginate
            $request->query->getInt('page', 1), /*page number*/
            3 /*limit per page*/
        );

        // Créer le rendu Twig
        return $this->render('destination/distinations.html.twig', [
            'destination' => $dest,
        ]);
    }
    


    #[Route('/new', name: 'app_destination_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        $destination = new Destination();
        $form = $this->createForm(DestinationType::class, $destination);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) 
        {
            
            $brochureFile = $form->get('img')->getData();

            // this condition is needed because the 'brochure' field is not required
            // so the PDF file must be processed only when a file is uploaded
            if ($brochureFile) {
                $originalFilename = pathinfo($brochureFile->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$brochureFile->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $brochureFile->move(
                        $this->getParameter('destination_img'),
                        $newFilename
                    );
                    $destination->setImg($this->getParameter('destination_img').'/'.$newFilename);

                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }
                
            }
            $entityManager->persist($destination);
            $entityManager->flush();

            return $this->redirectToRoute('app_destination_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('destination/new.html.twig', [
            'destination' => $destination,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_destination_show', methods: ['GET'])]
    public function show(Destination $destination): Response
    {
        return $this->render('destination/show.html.twig', [
            'destination' => $destination,
        ]);
    }

    #[Route('/details/{id}', name: 'app_destination_showdetails', methods: ['GET'])]
    public function showdetails(Destination $destination): Response
    {
        return $this->render('destination/destinationdetails.html.twig', [
            'destination' => $destination,
        ]);
    }

    
  

    #[Route('/{id}/edit', name: 'app_destination_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Destination $destination, EntityManagerInterface $entityManager, SluggerInterface $slugger,$id ): Response
    {
        $activite = $this->entityManager->getRepository(Destination::class)->find($id);
        $form = $this->createForm(DestinationType::class, $activite);

        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            $image = $request->files->get('destination')['img'];
            $uploadsDirectory = $this->getParameter('destination_img');
            $filename = md5(uniqid()) . '.' . $image->guessExtension();
            $image->move(
                $uploadsDirectory,
                $filename
            );
        
          //  $activite->setImg($filename);
            $activite->setImg($this->getParameter('destination_img').'/'.$filename);

            $entityManager->flush();
        
            return $this->redirectToRoute('app_destination_index');
        }
        
        return $this->render('destination/edit.html.twig', [
            'destination' => "destination",
            'form' => $form->createView(),
        ]);
    }

    #[Route('/{id}', name: 'app_destination_delete', methods: ['POST'])]
    public function delete(Request $request, Destination $destination, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$destination->getId(), $request->request->get('_token'))) {
            $entityManager->remove($destination);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_destination_index', [], Response::HTTP_SEE_OTHER);
    }




    
}

