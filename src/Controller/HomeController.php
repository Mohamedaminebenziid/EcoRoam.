<?php

declare(strict_types=1);

namespace App\Controller;

use App\Form\ChatType;
use App\Service\ChatGPTClient;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;

class HomeController extends AbstractController
{
    #[Route('/homef', name: 'app_home', methods: ['GET'])]
    public function index(): Response
    {
        // You can replace this with whatever logic you want for your homepage
        return $this->render('home/home.html.twig');
    }
}