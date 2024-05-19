<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert; 

#[ORM\Entity(repositoryClass: ProduitRepository::class)]
class Produit
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

   
    #[ORM\Column(type: "string", length: 255, nullable: false)]
    #[Assert\NotBlank(message: "the space 'intitule' can not be empty.")]
    private ?string $intitule = null;

  
    #[ORM\Column(type: "string", length: 255, nullable: false)]
    #[Assert\NotBlank(message: "the space 'Description' can not be empty.")]
    private ?string $description = null;

    #[ORM\Column(type: 'float')]
    #[Assert\NotNull(message: "the space 'prix' can not be empty.")]
    #[Assert\Type(type: 'numeric', message: "Le champ 'prix' must be a number.")]
    public ?float $prix = null;

    #[ORM\Column]
    #[Assert\NotNull(message: "the space 'stock' can not be empty.")]
    #[Assert\Type(type: 'numeric', message: "the space 'stock' must be a number.")]
    private ?int $stock = null;

    #[ORM\ManyToOne(inversedBy: 'produit')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Categorie $categorie = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "the space 'intitule' can not be empty.")]
    private ?string $image = null;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIntitule(): ?string
    {
        return $this->intitule;
    }

    public function setIntitule(?string $intitule): static
    {
        $this->intitule = $intitule;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(?float $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    public function getStock(): ?int
    {
        return $this->stock;
    }

    public function setStock(?int $stock): static
    {
        $this->stock = $stock;

        return $this;
    }

    public function getCategorie(): ?Categorie
    {
        return $this->categorie;
    }

    public function setCategorie(?Categorie $categorie): static
    {
        $this->categorie = $categorie;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): static
    {
        $this->image = $image;

        return $this;
    }
}
