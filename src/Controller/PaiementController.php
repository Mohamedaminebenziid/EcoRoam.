<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class PaiementController extends AbstractController
{
    #[Route('/paiement', name: 'app_paiement')]
    public function index(): Response
    {
        return $this->render('paiement/index.html.twig', [
            'controller_name' => 'PaiementController',
        ]);
    }

    #[Route('/paiement', name: 'paiement_stripe')]
    public function paiement(): RedirectResponse
    {\Stripe\Stripe::setApiKey($stripeSecretKey);
        header('Content-Type: application/json');
        
        $YOUR_DOMAIN = 'http://localhost:4242';
        
        $checkout_session = \Stripe\Checkout\Session::create([
          'line_items' => [[
            # Provide the exact Price ID (e.g. pr_1234) of the product you want to sell
            'price' => '{{PRICE_ID}}',
            'quantity' => 1,
          ]],
          'mode' => 'payment',
          'success_url' => $YOUR_DOMAIN . '/success.html',
          'cancel_url' => $YOUR_DOMAIN . '/cancel.html',
          'automatic_tax' => [
            'enabled' => true,
          ],
        ]);
        return $this->render('paiement/index.html.twig', [
            'controller_name' => 'PaiementController',
        ]);
    }
}
