<?php
// src/Controller/ParticipationController.php

namespace App\Controller;

use App\Form\ParticipationFormType;
use App\Service\SmsSender;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

#[Route('/participate')]
class ParticipationController extends AbstractController
{
    private $smsSender;

    public function __construct(SmsSender $smsSender)
    {
        $this->smsSender = $smsSender;
    }

    #[Route("/participation/form", name:"participation_form")]
    public function showForm(Request $request): Response
    {
        $form = $this->createForm(ParticipationFormType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $formData = $form->getData();
            // Extract form data and handle participation
            $name = $formData['name'];
            $idCard = $formData['id_card'];
            $phoneNumber = $formData['phone_number'];

            // Construct SMS message
            $message = sprintf('Thank you, %s! Your ID card number is %s. Thank you for joining!', $name, $idCard);

            // Send SMS message
            $this->smsSender->sendSms($phoneNumber, $message);

            // Redirect to a thank you page
            return $this->redirectToRoute('thank_you');
        }

        return $this->render('participation/form.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    #[Route("/participation/submit", name:"participation_submit", methods:["POST"])]
    public function handleParticipation(Request $request): Response
{
    // Assuming form data is submitted via POST method
    $name = $request->request->get('name');
    $idCard = $request->request->get('id_card');
    $phoneNumber = $request->request->get('phone_number');

    // Construct SMS message
    $message = sprintf('Thank you, %s! Your ID card number is %s. Thank you for joining!', $name, $idCard);

    try {
        // Send SMS message
        $this->smsSender->sendSms($phoneNumber, $message);

        // Redirect to a thank you page
        return $this->redirectToRoute('thank_you');
    } catch (\RuntimeException $e) {
        // Handle the exception, e.g., log the error
        // Then return an appropriate response
        return new Response($e->getMessage(), Response::HTTP_INTERNAL_SERVER_ERROR);
    }
}

    #[Route('/thank-you', name: 'thank_you')]
    public function thankYou(): Response
    {
        return $this->render('participation/thank_you.html.twig');
    }
}
