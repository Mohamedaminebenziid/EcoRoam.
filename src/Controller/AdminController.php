<?php

namespace App\Controller;

use App\Entity\Categorie;
use App\Entity\Produit;
use App\Form\CategorieFormType;
use App\Form\ProduitFormType;
use App\Repository\CategorieRepository;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;

class AdminController extends AbstractController
{
    #[Route('/admin', name: 'app_admin')]
    public function index(): Response
    {
        return $this->render('admin/index.html.twig', [
            'controller_name' => 'AdminController',
        ]);
    }
     // ----------------------CATEGORIES---------------------------------
    #[Route('/admin/addCategorie', name: 'app_admin_addCategorie')]
    public function addCategorie(Request $req, EntityManagerInterface $entityManagerInterface, CategorieRepository $categorieRepository): Response
    {
        $categorie = new Categorie();
        $form = $this->createForm(CategorieFormType::class, $categorie);
        $form->handleRequest($req);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManagerInterface->persist($categorie);
            $entityManagerInterface->flush();
            return $this->redirectToRoute('app_admin_addCategorie');
        }
        $liste = $categorieRepository->findAll();
     
        return $this->renderForm('admin/addCategorie.html.twig', [
            'formAdd' => $form,
            'categorie' => $liste,
        ]);
    }

       
        #[Route('/admin/listcategorie', name: 'app_list_categorie')]
        public function listcategorie(CategorieRepository $categorieRepository)
        {
            $categorie = $categorieRepository->findAll();
            return $this->render('admin/ajouterCategorie.html.twig', [
                'categorie' => $categorie,
            ]);
        }
        #[Route('/admin/Categorie/edit/{id}', name: 'app_edit_categorie')]
        public function editCategorie($id, CategorieRepository $CategorieRepository, Request $req, EntityManagerInterface $entityManagerInterface)
        {
            $categorie = $CategorieRepository->find($id);
    
            $form = $this->createForm(CategorieFormType::class, $categorie);
    
            $form->handleRequest($req);
            if ($form->isSubmitted()) {
                $entityManagerInterface->persist($categorie);
                $entityManagerInterface->flush();
                return $this->redirectToRoute('app_admin_addCategorie');
            }
            return $this->renderForm('admin/addCategorie.html.twig', ['formAdd' => $form]);
        }
        #[Route('/admin/Categorie/delete/{id}', name: 'app_delete_categorie')]
        public function deleteCategorie($id, EntityManagerInterface $em, CategorieRepository $CategorieRepository)
        {
            $categorie = $CategorieRepository->find($id);
            $em->remove($categorie);
            $em->flush();
    
            return $this->redirectToRoute('app_admin_addCategorie');
        }
        // --------------------------------PRODUIT------------------------------

        #[Route('/admin/Addproduct', name: 'app_admin_addProduct')]
        public function addProduct(Request $req, EntityManagerInterface $entityManagerInterface, SluggerInterface $slugger)
        {
            $produit = new Produit();
            $form = $this->createForm(ProduitFormType::class, $produit);
            $form->handleRequest($req);
            if ($form->isSubmitted() && $form->isValid()) {
                $imageFile = $form->get('image')->getData();
    
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
    
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename . '-' . uniqid() . '.' . $imageFile->guessExtension();
    
                $imageFile->move(
                    $this->getParameter('upload_directory'),
                    $newFilename
                );
                $produit->setImage(($newFilename));
    
                $entityManagerInterface->persist($produit);
                $entityManagerInterface->flush();
                return $this->redirectToRoute('app_list_product');
            }
            return $this->renderForm('admin/addProduit.html.twig', [
                'productForm' => $form,
            ]);
        }
        #[Route('/admin/listProduct', name: 'app_list_product')]
        public function listProduct(ProduitRepository $ProduitRepository)
        {
            $produit = $ProduitRepository->findAll();
            return $this->render('admin/productList.html.twig', [
                'ListeProduit' => $produit,
            ]);
        }
        #[Route('/admin/Product/edit/{id}', name: 'app_edit_produit')]
        public function editProduit($id, ProduitRepository $ProduitRepository, Request $req, EntityManagerInterface $entityManagerInterface, SluggerInterface $slugger)
        {
            $produit = $ProduitRepository->find($id);
    
            if (!$produit) {
                throw $this->createNotFoundException('Product not found');
            }
    
            $form = $this->createForm(ProduitFormType::class, $produit);
            $form->handleRequest($req);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $imageFile = $form->get('image')->getData();
    
                if ($imageFile) {
                    $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
    
                    $safeFilename = $slugger->slug($originalFilename);
                    $newFilename = $safeFilename . '-' . uniqid() . '.' . $imageFile->guessExtension();
    
                    $imageFile->move(
                        $this->getParameter('upload_directory'),
                        $newFilename
                    );
    
                    $produit->setImage($newFilename);
                }
    
                $entityManagerInterface->persist($produit);
                $entityManagerInterface->flush();
    
                return $this->redirectToRoute('app_list_product');
            }
    
            return $this->renderForm('admin/addProduit.html.twig', ['productForm' => $form]);
        }
        #[Route('/admin/Produit/delete/{id}', name: 'app_delete_produit')]
        public function deleteProduit($id, EntityManagerInterface $em, ProduitRepository $ProduitRepository)
        {
            $produit = $ProduitRepository->find($id);
            $em->remove($produit);
            $em->flush();
    
            return $this->redirectToRoute('app_list_product');
        }
    
}
