<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class CartController extends AbstractController
{
    
    #[Route('/cart', name: 'app_cart')]
    public function cart(SessionInterface $session, ProduitRepository $produitRepository)
    {
        $cart = $session->get('cart', []);

        // On initialise des variables
        $data = [];
        $total = 0;
        $cartItemCount = 0;

        foreach ($cart as $id => $quantite) {
            $produit = $produitRepository->find($id);

            $data[] = [
                'produit' => $produit,
                'quantite' => $quantite
            ];
            $cartItemCount += $quantite;
            $session->set('cartItemCount', $cartItemCount);
            $total += $produit->getPrix() * $quantite;
        }
        return $this->renderForm('cart/cart.html.twig', [
            'data' => $data,
            'cartItemCount' => $cartItemCount,
            'total' => $total,
        ]);
    }

    #[Route('/add/{id}', name: 'cart_add')]
    public function add(Produit $produit, SessionInterface $session)
    {
        //On récupère l'id du produit
        $id = $produit->getId();

        // On récupère le cart existant
        $cart = $session->get('cart', []);

        if (empty($cart[$id])) {
            $cart[$id] = 1;
        } else {
            $cart[$id]++;
        }

        $session->set('cart', $cart);

        //On redirige vers la page du cart
        return $this->redirectToRoute('app_cart');
    }

    #[Route('/remove/{id}', name: 'cart_remove')]
    public function remove(Produit $produit, SessionInterface $session)
    {
        //On récupère l'id du produit
        $id = $produit->getId();

        // On récupère le cart existant
        $cart = $session->get('cart', []);

        // On retire le produit du cart s'il n'y a qu'1 exemplaire
        // Sinon on décrémente sa quantité
        if (!empty($cart[$id])) {
            if ($cart[$id] > 1) {
                $cart[$id]--;
            } else {
                unset($cart[$id]);
            }
        }

        $session->set('cart', $cart);

        //On redirige vers la page du cart
        return $this->redirectToRoute('app_cart');
    }

    #[Route('/delete/{id}', name: 'cart_delete')]
    public function delete(Produit $produit, SessionInterface $session)
    {
        //On récupère l'id du produit
        $id = $produit->getId();

        // On récupère le cart existant
        $cart = $session->get('cart', []);

        if (!empty($cart[$id])) {
            unset($cart[$id]);
        }

        $session->set('cart', $cart);

        //On redirige vers la page du cart
        return $this->redirectToRoute('app_cart');
    }

    #[Route('/empty', name: 'empty')]
    public function empty(SessionInterface $session)
    {
        $session->remove('cart');

        return $this->redirectToRoute('app_cart');
    }

   

    #[Route('/edit/{id}', name: 'cart_edit')]
    public function edit(Produit $produit, Request $request, SessionInterface $session)
    {
        $id = $produit->getId();
        $cart = $session->get('cart', []);

        if ($request->isMethod('POST')) {
            $newQuantity = (int)$request->request->get('new_quantity', 1);

            if ($newQuantity > 0) {
                $cart[$id] = $newQuantity;
                $session->set('cart', $cart);
            }
        }

        return $this->redirectToRoute('app_cart');
    }
    #[Route('/order', name: 'app_order')]
    public function order(): Response
    {
        return $this->render('cart/order.html.twig', [
            'controller_name' => 'ClientController',
        ]);
    }
}
