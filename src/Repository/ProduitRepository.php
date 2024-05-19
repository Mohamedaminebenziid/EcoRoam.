<?php

namespace App\Repository;

use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\Tools\Pagination\Paginator;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Produit>
 *
 * @method Produit|null find($id, $lockMode = null, $lockVersion = null)
 * @method Produit|null findOneBy(array $criteria, array $orderBy = null)
 * @method Produit[]    findAll()
 * @method Produit[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Produit::class);
    }

    public function findProduitsPaginated(int $page, int $limit = 6): array
    {
        $limit = abs($limit);

        $result = [];

        $query = $this->getEntityManager()->createQueryBuilder()
            ->select('p')  
            ->from('App\Entity\Produit', 'p')  
            ->setMaxResults($limit)
            ->setFirstResult(($page * $limit) - $limit);

        $paginator = new Paginator($query);
        $data = $paginator->getQuery()->getResult();

        // On vérifie qu'on a des données
        if (empty($data)) {
            return $result;
        }

        // On calcule le nombre de pages
        $pages = ceil($paginator->count() / $limit);

        // On remplit le tableau
        $result['data'] = $data;
        $result['pages'] = $pages;
        $result['page'] = $page;
        $result['limit'] = $limit;

        return $result;
    }
    public function findAllSortedByCategorie(?string $categorie): array
    {
        $queryBuilder = $this->createQueryBuilder('p')
            ->leftJoin('p.categorie', 'c')
            ->addOrderBy('c.intitule', 'ASC') // Trie par le nom de la catégorie en ordre croissant
            ->addOrderBy('p.intitule', 'ASC'); // Ensuite, trie par l'intitulé du produit en ordre croissant

        if ($categorie) {
            $queryBuilder
                ->andWhere('c.intitule = :categorie')
                ->setParameter('categorie', $categorie);
        }

        return $queryBuilder
            ->getQuery()
            ->getResult();
    }
//    /**
//     * @return Produit[] Returns an array of Produit objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('p.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Produit
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
