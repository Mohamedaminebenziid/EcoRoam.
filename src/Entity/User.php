<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;

#[ORM\Entity(repositoryClass: UserRepository::class)]
#[UniqueEntity(fields: ['username'], message: 'There is already an account with this username')]
class User implements UserInterface, PasswordAuthenticatedUserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $username = null;

    #[ORM\Column(length: 255)]
    private ?string $email = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $adresse = null;

    #[ORM\Column(length: 255)]
    private ?string $password = null;

    #[ORM\Column(type: 'json')]
    private array $roles = [];

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $tel_number = null;

    #[ORM\Column(type: 'boolean')]
    private bool $is_banned = false;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUsername(): ?string
    {
        return $this->username;
    }

    public function setUsername(string $username): self
    {
        $this->username = $username;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(?string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getRoles(): array
    {
        $roles = $this->roles;
        // guarantee every user at least has ROLE_USER
        $roles[] = 'ROLE_USER';

        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }

    public function eraseCredentials() {}

    public function getSalt(): ?string
    {
        // Implement your logic to return the salt (if applicable)
        // Return null if the user doesn't have a salt
        return null; // or return the actual salt value
    }

    public function getUserIdentifier(): string
    {
        return (string) $this->username;
    }

    public function isBanned(): bool
    {
        return $this->is_banned;
    }

    public function setIsBanned(bool $isBanned): self
    {
        $this->is_banned = $isBanned;

        return $this;
    }

    public function getTelNumber(): ?string
    {
        return $this->tel_number;
    }

    public function setTelNumber(?string $telNumber): self
    {
        $this->tel_number = $telNumber;

        return $this;
    }

    public function isVerified(): bool
    {
        // Implement verification logic if needed
        return true; // or false based on your application logic
    }

    public function __construct()
    {
        // Set default values if needed
        $this->is_banned = false;
    }
}
