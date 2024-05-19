<?php

namespace App\Entity;

use App\Repository\CertificateRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: CertificateRepository::class)]
class Certificate
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Veuillez saisir le nom du participant")]
    #[Assert\Length(max:255, maxMessage:"Le nom du participant ne peut pas dépasser {{ limit }} caractères")]
    private ?string $P_name = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Veuillez saisir le titre du cours")]
    #[Assert\Length(max:255, maxMessage:"Le titre du cours ne peut pas dépasser {{ limit }} caractères")]
    private ?string $C_title = null;

    
    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\NotBlank(message:"Veuillez saisir une date valide")]
    private ?\DateTimeInterface $date = null;


    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Veuillez saisir la signature")]
    #[Assert\Length(max:255, maxMessage:"La signature ne peut pas dépasser {{ limit }} caractères")]
    private ?string $signature = null;

    #[ORM\ManyToOne(inversedBy: 'certificates')]
    private ?Course $cours = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPName(): ?string
    {
        return $this->P_name;
    }

    public function setPName(string $P_name): static
    {
        $this->P_name = $P_name;

        return $this;
    }

    public function getCTitle(): ?string
    {
        return $this->C_title;
    }

    public function setCTitle(string $C_title): static
    {
        $this->C_title = $C_title;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): static
    {
        $this->date = $date;

        return $this;
    }
    public function getSignature(): ?string
    {
        return $this->signature;
    }

    public function setSignature(string $signature): static
    {
        $this->signature = $signature;

        return $this;
    }

    public function getCours(): ?Course
    {
        return $this->cours;
    }

    public function setCours(?Course $cours): static
    {
        $this->cours = $cours;

        return $this;
    }
}
