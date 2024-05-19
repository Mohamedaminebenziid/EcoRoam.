<?php

namespace App\Entity;

use App\Repository\ReservationRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ReservationRepository::class)]
class Reservation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

  

      /**
     * @Assert\NotBlank(message="The start date is required")
     * @Assert\GreaterThan(value="today", message="The start date must be after today")
     */
    #[ORM\Column(type: Types::DATE_MUTABLE)]

    private $startdate;

    /**
     * @Assert\NotBlank(message="The end date is required")
     * @Assert\GreaterThan(value="today", message="The end date must be after today")
     * @Assert\GreaterThan(propertyPath="startdate", message="The end date must be after the start date")
     */
    #[ORM\Column(type: Types::DATE_MUTABLE)]

    private $enddate;

    #[ORM\Column]
    private ?int $number = null;

    #[ORM\Column]
    private ?float $totalprice = null;

    #[ORM\Column(length: 255)]
    private ?string $status = null;

    #[ORM\ManyToOne(inversedBy: 'reservation')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Destination $Destination = null;



    public function __construct()
    {
        // Set startdate to today's date when creating a new instance
        $this->startdate = new \DateTime();
        $this->enddate = new \DateTime();

    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getStartdate(): ?\DateTimeInterface
    {
        return $this->startdate;
    }

    public function setStartdate(\DateTimeInterface $startdate): static
    {
        $this->startdate = $startdate;

        return $this;
    }

    public function getEnddate(): ?\DateTimeInterface
    {
        return $this->enddate;
    }

    public function setEnddate(\DateTimeInterface $enddate): static
    {
        $this->enddate = $enddate;

        return $this;
    }

    public function getNumber(): ?int
    {
        return $this->number;
    }

    public function setNumber(int $number): static
    {
        $this->number = $number;

        return $this;
    }

    public function getTotalprice(): ?float
    {
        return $this->totalprice;
    }

    public function setTotalprice(float $totalprice): static
    {
        $this->totalprice = $totalprice;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(string $status): static
    {
        $this->status = $status;

        return $this;
    }

    public function getDestination(): ?Destination
    {
        return $this->Destination;
    }

    public function setDestination(?Destination $Destination): static
    {
        $this->Destination = $Destination;

        return $this;
    }
}
