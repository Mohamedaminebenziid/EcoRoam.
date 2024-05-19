<?php

namespace App\Entity;

use App\Repository\CourseRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
#[ORM\Entity(repositoryClass: CourseRepository::class)]
class Course
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(max: 255)]
    private ?string $title = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(max: 255)]
    private ?string $Descrition = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    private ?string $Duration = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    private ?string $Difficulty = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    private ?string $Category = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank]
    private ?string $image = null;

    #[ORM\OneToMany(targetEntity: Certificate::class, mappedBy: 'cours')]
    private Collection $certificates;

    public function __construct()
    {
        $this->certificates = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): static
    {
        $this->title = $title;

        return $this;
    }

    public function getDescrition(): ?string
    {
        return $this->Descrition;
    }

    public function setDescrition(string $Descrition): static
    {
        $this->Descrition = $Descrition;

        return $this;
    }

    public function getDuration(): ?string
    {
        return $this->Duration;
    }

    public function setDuration(string $Duration): static
    {
        $this->Duration = $Duration;

        return $this;
    }

    public function getDifficulty(): ?string
    {
        return $this->Difficulty;
    }

    public function setDifficulty(string $Difficulty): static
    {
        $this->Difficulty = $Difficulty;

        return $this;
    }

    public function getCategory(): ?string
    {
        return $this->Category;
    }

    public function setCategory(string $Category): static
    {
        $this->Category = $Category;

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

    /**
     * @return Collection<int, Certificate>
     */
    public function getCertificates(): Collection
    {
        return $this->certificates;
    }

    public function addCertificate(Certificate $certificate): static
    {
        if (!$this->certificates->contains($certificate)) {
            $this->certificates->add($certificate);
            $certificate->setCours($this);
        }

        return $this;
    }

    public function removeCertificate(Certificate $certificate): static
    {
        if ($this->certificates->removeElement($certificate)) {
            // set the owning side to null (unless already changed)
            if ($certificate->getCours() === $this) {
                $certificate->setCours(null);
            }
        }

        return $this;
    }
}