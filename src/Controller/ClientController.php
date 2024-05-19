<?php

namespace App\Controller;

use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ClientController extends AbstractController
{
    #[Route('/index', name: 'app_client')]
    public function index(): Response
    {
        return $this->render('client/index.html.twig', [
            'controller_name' => 'ClientController',
        ]);
    }
    #[Route('/shop', name: 'app_shop')]
        public function listProduct(ProduitRepository $ProduitRepository,Request $request)
        {
            $page = $request->query->getInt('page', 1);
        $pagination = $ProduitRepository->findProduitsPaginated($page, 3);

        $categorieSelectionnee = $request->query->get('categorie');
        $produits = $ProduitRepository->findAllSortedByCategorie($categorieSelectionnee);
            return $this->render('client/shop.html.twig', [
                'Produit' => $pagination,
                'produitsParCat' => $produits,
            ]);
        }
       

        #[Route('/product-details/{id}', name: 'product_details')]
        public function productdetails($id,ProduitRepository $ProduitRepository)
        {
            $produit = $ProduitRepository->find($id);
            return $this->render('client/product-details.html.twig', [
                'Produit' => $produit,
            ]);
        }
    
}
