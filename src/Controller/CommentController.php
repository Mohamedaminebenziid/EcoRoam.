<?php

namespace App\Controller;

use App\Entity\Comment;
use App\Form\CommentType;
use App\Repository\CommentRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/comment')]
class CommentController extends AbstractController
{
    #[Route('/', name: 'app_comment_index', methods: ['GET'])]
    public function index(CommentRepository $commentRepository): Response
    {
        return $this->render('comment/index.html.twig', [
            'comments' => $commentRepository->findAll(),
        ]);
    }
    #[Route('/comments', name: 'app_comment_front_index', methods: ['GET'])]
    public function indexfront(CommentRepository $commentRepository): Response
    {
        return $this->render('comment/afficher.html.twig', [
            'comments' => $commentRepository->findAll(),
        ]);
    }

    #[Route('/afficher', name: 'app_comment_afficher_index', methods: ['GET'])]
    public function indexafficher(CommentRepository $commentRepository): Response
    {
        return $this->render('comment/afficher.html.twig', [
            'comments' => $commentRepository->findAll(),
        ]);
    }




#[Route('/ajoutercom', name: 'app_comment_ajouter', methods: ['GET', 'POST'])]
public function ajouter(Request $request, EntityManagerInterface $entityManager): Response
{
    $comment = new Comment();
    $form = $this->createForm(CommentType::class, $comment);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $entityManager->persist($comment);
        $entityManager->flush();
    }

    return $this->render('comment/ajputer.html.twig', [
        'comment' => $comment,
        'form' => $form->createView(),
    ]);
}
     

    #[Route('/new', name: 'app_comment_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $comment = new Comment();
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($comment);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_comment_index');
        }
    
        return $this->render('comment/new.html.twig', [
            'comment' => $comment,
            'form' => $form->createView(),
        ]);
    }
    

    #[Route('/{id}', name: 'app_comment_show', methods: ['GET'])]
    public function show(Comment $comment): Response
    {
        return $this->render('comment/show.html.twig', [
            'comment' => $comment,
        ]);
    }



    #[Route('{id}/edit', name: 'app_comment_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Comment $comment, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_comment_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('comment/edit.html.twig', [
            'comment' => $comment,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_comment_delete', methods: ['POST'])]
    public function delete(Request $request, Comment $comment, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$comment->getId(), $request->request->get('_token'))) {
            $entityManager->remove($comment);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_comment_index', [], Response::HTTP_SEE_OTHER);
    }

   ##front
   #[Route('/ajouter', name: 'app_comment_create_new', methods: ['GET', 'POST'])]
    public function ajouterc(Request $request, EntityManagerInterface $entityManager): Response
    {
        $comment = new Comment();
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($comment);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_comment_front_index');
        }
    
        return $this->render('comment/ajputer.html.twig', [
            'comment' => $comment,
            'form' => $form->createView(),
        ]);
    }
    #[Route('/{id}/editt', name: 'app_comment_front_edit', methods: ['GET', 'POST'])]
    public function editfront(Request $request, Comment $comment, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
    
            return $this->redirectToRoute('app_comment_front_index', [], Response::HTTP_SEE_OTHER);
        }
    
        return $this->renderForm('comment/editfront.html.twig', [
            'comment' => $comment,
            'form' => $form,
        ]);
    }
    
    
    #[Route('/{id}', name: 'app_comment_delete', methods: ['POST'])]
    public function deletefront(Request $request, Comment $comment, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$comment->getId(), $request->request->get('_token'))) {
            $entityManager->remove($comment);
            $entityManager->flush();
        }

        return $this->redirectToRoute('base');
    }




     
     
    

        


}
