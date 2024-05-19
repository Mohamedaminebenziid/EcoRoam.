<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240308105627 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE activities (id INT AUTO_INCREMENT NOT NULL, events_id INT DEFAULT NULL, name VARCHAR(255) NOT NULL, img VARCHAR(255) NOT NULL, state VARCHAR(255) NOT NULL, price NUMERIC(10, 2) NOT NULL, description VARCHAR(255) NOT NULL, INDEX IDX_B5F1AFE59D6A1065 (events_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE categorie (id INT AUTO_INCREMENT NOT NULL, intitule VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE certificate (id INT AUTO_INCREMENT NOT NULL, cours_id INT DEFAULT NULL, p_name VARCHAR(255) NOT NULL, c_title VARCHAR(255) NOT NULL, date DATE NOT NULL, signature VARCHAR(255) NOT NULL, INDEX IDX_219CDA4A7ECF78B0 (cours_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE comment (id INT AUTO_INCREMENT NOT NULL, forum_id INT DEFAULT NULL, content VARCHAR(255) NOT NULL, img VARCHAR(255) NOT NULL, rate DOUBLE PRECISION NOT NULL, INDEX IDX_9474526C29CCBAD0 (forum_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE course (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, descrition VARCHAR(255) NOT NULL, duration VARCHAR(255) NOT NULL, difficulty VARCHAR(255) NOT NULL, category VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE destination (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, img VARCHAR(255) NOT NULL, state VARCHAR(255) NOT NULL, address VARCHAR(255) NOT NULL, prix DOUBLE PRECISION NOT NULL, description VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE events (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, price NUMERIC(10, 2) NOT NULL, state VARCHAR(255) NOT NULL, img VARCHAR(255) NOT NULL, date VARCHAR(255) NOT NULL COMMENT \'(DC2Type:dateinterval)\', description VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE events_activities (events_id INT NOT NULL, activities_id INT NOT NULL, INDEX IDX_60081A649D6A1065 (events_id), INDEX IDX_60081A642A4DB562 (activities_id), PRIMARY KEY(events_id, activities_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE forum (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE produit (id INT AUTO_INCREMENT NOT NULL, categorie_id INT NOT NULL, intitule VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, prix DOUBLE PRECISION NOT NULL, stock INT NOT NULL, image VARCHAR(255) NOT NULL, INDEX IDX_29A5EC27BCF5E72D (categorie_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reservation (id INT AUTO_INCREMENT NOT NULL, destination_id INT NOT NULL, startdate DATE NOT NULL, enddate DATE NOT NULL, number INT NOT NULL, totalprice DOUBLE PRECISION NOT NULL, status VARCHAR(255) NOT NULL, INDEX IDX_42C84955816C6140 (destination_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, username VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, adresse VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, roles JSON NOT NULL COMMENT \'(DC2Type:json)\', is_verified TINYINT(1) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE activities ADD CONSTRAINT FK_B5F1AFE59D6A1065 FOREIGN KEY (events_id) REFERENCES events (id)');
        $this->addSql('ALTER TABLE certificate ADD CONSTRAINT FK_219CDA4A7ECF78B0 FOREIGN KEY (cours_id) REFERENCES course (id)');
        $this->addSql('ALTER TABLE comment ADD CONSTRAINT FK_9474526C29CCBAD0 FOREIGN KEY (forum_id) REFERENCES forum (id)');
        $this->addSql('ALTER TABLE events_activities ADD CONSTRAINT FK_60081A649D6A1065 FOREIGN KEY (events_id) REFERENCES events (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE events_activities ADD CONSTRAINT FK_60081A642A4DB562 FOREIGN KEY (activities_id) REFERENCES activities (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE produit ADD CONSTRAINT FK_29A5EC27BCF5E72D FOREIGN KEY (categorie_id) REFERENCES categorie (id)');
        $this->addSql('ALTER TABLE reservation ADD CONSTRAINT FK_42C84955816C6140 FOREIGN KEY (destination_id) REFERENCES destination (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE activities DROP FOREIGN KEY FK_B5F1AFE59D6A1065');
        $this->addSql('ALTER TABLE certificate DROP FOREIGN KEY FK_219CDA4A7ECF78B0');
        $this->addSql('ALTER TABLE comment DROP FOREIGN KEY FK_9474526C29CCBAD0');
        $this->addSql('ALTER TABLE events_activities DROP FOREIGN KEY FK_60081A649D6A1065');
        $this->addSql('ALTER TABLE events_activities DROP FOREIGN KEY FK_60081A642A4DB562');
        $this->addSql('ALTER TABLE produit DROP FOREIGN KEY FK_29A5EC27BCF5E72D');
        $this->addSql('ALTER TABLE reservation DROP FOREIGN KEY FK_42C84955816C6140');
        $this->addSql('DROP TABLE activities');
        $this->addSql('DROP TABLE categorie');
        $this->addSql('DROP TABLE certificate');
        $this->addSql('DROP TABLE comment');
        $this->addSql('DROP TABLE course');
        $this->addSql('DROP TABLE destination');
        $this->addSql('DROP TABLE events');
        $this->addSql('DROP TABLE events_activities');
        $this->addSql('DROP TABLE forum');
        $this->addSql('DROP TABLE produit');
        $this->addSql('DROP TABLE reservation');
        $this->addSql('DROP TABLE user');
        $this->addSql('DROP TABLE messenger_messages');
    }
}
