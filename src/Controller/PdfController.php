<?php

namespace App\Controller;

use App\Entity\Reservation;
use App\Repository\ReservationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Snappy\Pdf;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Dompdf\Dompdf;

class PdfController extends AbstractController
{

    private $reservationRepository;

    public function __construct(ReservationRepository $reservationRepository)
    {
        $this->reservationRepository = $reservationRepository;
    }

   /**
     * @Route("/pdf/generator", name="pdf_generator")
     */
    public function index(): Response
    {
         
        $data = [
            'imageSrc'  => $this->imageToBase64($this->getParameter('kernel.project_dir') . '/public/img/profile.png'),
            'name'         => 'John Doe',
            'address'      => 'USA',
            'mobileNumber' => '000000000',
            'email'        => 'john.doe@email.com'
        ];
        $html =  $this->renderView('pdf_generator/index.html.twig', $data);
        $dompdf = new Dompdf();
        $dompdf->loadHtml($html);
        $dompdf->render();
        return new Response (
            $dompdf->stream('resume', ["Attachment" => false]),
            Response::HTTP_OK,
            ['Content-Type' => 'application/pdf']
        );
    }
 
    private function imageToBase64($path) {
        $path = $path;
        $type = pathinfo($path, PATHINFO_EXTENSION);
        $data = file_get_contents($path);
        $base64 = 'data:image/' . $type . ';base64,' . base64_encode($data);
        return $base64;
    }
}

