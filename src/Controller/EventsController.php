<?php

namespace App\Controller;

use App\Entity\Events;
use App\Form\EventsType;
use App\Repository\EventsRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File;
use App\Entity\Activities;


#[Route('/events')]
class EventsController extends AbstractController
{

   


    #[Route('/', name: 'app_events_index', methods: ['GET'])]
    public function index(EventsRepository $eventsRepository): Response
    {
        return $this->render('events/index.html.twig', [
            'events' => $eventsRepository->findAll(),
        ]);
    }

    #[Route('/events', name: 'app_events_indextemp', methods: ['GET'])]

    public function showDest(EntityManagerInterface $entityManager)
    {
        // Récupérer tous les articles
        $evt = $entityManager->getRepository(Events::class)->findAll();

    
        // Créer le rendu Twig
        return $this->render('events/events.html.twig', [
            'events' => $evt,
        ]);
    }

    #[Route('/new', name: 'app_events_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        $event = new Events();
      // Access id property on each activity
    // Do something with $id

        $form = $this->createForm(EventsType::class, $event);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
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
                    $event->setImg($this->getParameter('destination_img').'/'.$newFilename);
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }
                $event->setImg($newFilename);
            }
            $entityManager->persist($event);
            $entityManager->flush();

            return $this->redirectToRoute('app_events_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('events/new.html.twig', [
            'event' => $event,
            'eventsform' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_events_show', methods: ['GET'])]
    public function show(Events $event): Response
    {
        return $this->render('events/show.html.twig', [
            'event' => $event,
        ]);
    }

    #[Route('/events/{id}', name: 'app_events_showdetails', methods: ['GET'])]
    public function showdetails(Events $events): Response
    {
        return $this->render('events/eventsdetails.html.twig', [
            'events' => $events,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_events_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Events $event, EntityManagerInterface $entityManager, SluggerInterface $slugger, $id): Response
{
    // Retrieve the activity
    $activity = $entityManager->getRepository(Events::class)->find($id);
    
    // Create the form and bind it to the activity entity
    $form = $this->createForm(EventsType::class, $activity);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // Retrieve the uploaded file from the request
        $imageFile = $form->get('img')->getData();

        // Check if a file was uploaded
        if ($imageFile) {
            // Generate a unique name for the file
            $newFilename = md5(uniqid()).'.'.$imageFile->guessExtension();

            // Move the file to the desired location
            try {
                $imageFile->move(
                    $this->getParameter('activities_img'),
                    $newFilename
                );
                $event->setImg($this->getParameter('activities_img').'/'.$newFilename);
            } catch (FileException $e) {
                // Handle file upload error
            }

            // Set the file name in the activity entity
            $event->setImg($this->getParameter('activities_img').'/'.$newFilename);
        }

        // Persist changes to the database
        $entityManager->flush();

        // Redirect the user
        return $this->redirectToRoute('app_events_index');
    }

    // Render the form
    return $this->render('events/edit.html.twig', [
        'event' => $event,
        'eventsform' => $form->createView(),
    ]);
}

    #[Route('/{id}', name: 'app_events_delete', methods: ['POST'])]
    public function delete(Request $request, Events $event, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$event->getId(), $request->request->get('_token'))) {
            $entityManager->remove($event);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_events_index', [], Response::HTTP_SEE_OTHER);
    }
}
