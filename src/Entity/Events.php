<?php

namespace App\Entity;

use App\Repository\EventsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: EventsRepository::class)]
class Events
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Veuillez ajouter un nom ")]
    private ?string $name = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    #[Assert\NotBlank(message: "Veuillez ajouter un prix.")]
    #[Assert\Regex(
        pattern: "/^\d+(\.\d{1,2})?$/",
        message: "Le prix doit être un nombre valide avec jusqu'à deux décimales."
    )]
    private ?string $price = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Veuillez ajouter un état.")]
    private ?string $state = null;

    #[ORM\Column(length: 255)]
    private ?string $img = null;

    #[ORM\Column]
    #[Assert\NotBlank(message: "Veuillez ajouter une date.")]
    private ?\DateInterval $date = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Veuillez ajouter une description.")]
    private ?string $description = null;

    #[ORM\OneToMany(targetEntity: Activities::class, mappedBy: 'events')]
    #[Assert\NotBlank(message: "Veuillez ajouter une activite.")]
    private Collection $Activities;

    #[ORM\ManyToMany(targetEntity: Activities::class, inversedBy: 'event')]
    private Collection $events;

    public function __construct()
    {
        $this->Activities = new ArrayCollection();
        $this->events = new ArrayCollection();
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

    public function getPrice(): ?string
    {
        return $this->price;
    }

    public function setPrice(string $price): static
    {
        $this->price = $price;

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

    public function getImg(): ?string
    {
        return $this->img;
    }

    public function setImg(string $img): static
    {
        $this->img = $img;

        return $this;
    }

    public function getDate(): ?\DateInterval
    {
        return $this->date;
    }

    public function setDate(\DateInterval $date): static
    {
        $this->date = $date;

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
     * @return Collection<int, Activities>
     */
    public function getActivities(): Collection
    {
        return $this->Activities;
    }

    public function addActivity(Activities $activity): static
    {
        if (!$this->Activities->contains($activity)) {
            $this->Activities->add($activity);
            $activity->setEvents($this);
        }

        return $this;
    }

    public function removeActivity(Activities $activity): static
    {
        if ($this->Activities->removeElement($activity)) {
            // set the owning side to null (unless already changed)
            if ($activity->getEvents() === $this) {
                $activity->setEvents(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Activities>
     */
    public function getEvents(): Collection
    {
        return $this->events;
    }

    public function addEvent(Activities $event): static
    {
        if (!$this->events->contains($event)) {
            $this->events->add($event);
        }

        return $this;
    }

    public function removeEvent(Activities $event): static
    {
        $this->events->removeElement($event);

        return $this;
    }
}
