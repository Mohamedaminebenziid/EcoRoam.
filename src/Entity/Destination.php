<?php

namespace App\Entity;

use App\Repository\DestinationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: DestinationRepository::class)]
class Destination
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;


    
    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le nom ne peut pas être vide.")]
    #[Assert\Length(max: 255, maxMessage: "Le nom ne peut pas dépasser {{ limit }} caractères.")]
    private ?string $name = null;

    #[ORM\Column(length: 255)]

    private ?string $img = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "L'état ne peut pas être vide.")]
    #[Assert\Length(max: 255, maxMessage: "L'état ne peut pas dépasser {{ limit }} caractères.")]
    private ?string $state = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "L'adresse ne peut pas être vide.")]
    #[Assert\Length(max: 255, maxMessage: "L'adresse ne peut pas dépasser {{ limit }} caractères.")]
    private ?string $address = null;

    #[ORM\Column]
    #[Assert\NotBlank(message: "Le prix ne peut pas être vide.")]
    #[Assert\Type(type: "float", message: "Le prix doit être un nombre.")]
    private ?float $prix = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "La description ne peut pas être vide.")]
    private ?string $description = null;

    #[ORM\OneToMany(targetEntity: Reservation::class, mappedBy: 'Destination')]
    private Collection $reservation;

    public function __construct()
    {
        $this->reservation = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): static
    {
        $this->name = $name;

        return $this;
    }

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): static
    {
        $this->img = $img;

        return $this;
    }

    public function getState(): ?string
    {
        return $this->state;
    }

    public function setState(string $state): static
    {
        $this->state = $state;

        return $this;
    }

    public function getAddress(): ?string
    {
        return $this->address;
    }

    public function setAddress(string $address): static
    {
        $this->address = $address;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }

    /**
     * @return Collection<int, Reservation>
     */
    public function getReservation(): Collection
    {
        return $this->reservation;
    }

    public function addReservation(Reservation $reservation): static
    {
        if (!$this->reservation->contains($reservation)) {
            $this->reservation->add($reservation);
            $reservation->setDestination($this);
        }

        return $this;
    }

    public function removeReservation(Reservation $reservation): static
    {
        if ($this->reservation->removeElement($reservation)) {
            // set the owning side to null (unless already changed)
            if ($reservation->getDestination() === $this) {
                $reservation->setDestination(null);
            }
        }

        return $this;
    }
}
