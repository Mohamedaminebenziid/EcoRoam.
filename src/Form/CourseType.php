<?php

namespace App\Form;

use App\Entity\Course;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class CourseType extends AbstractType
{
     public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('title')      
        ->add('image', FileType::class, [
            'label' => 'course image  (JPEG file)',


            // make it optional so you don't have to re-upload the PDF file
            // every time you edit the Product details
            'required' => false,
            'data_class' => null,

            // unmapped fields can't define their validation using attributes
            // in the associated entity, so you can use the PHP constraint classes
            'constraints' => [
                new File([
                    'maxSize' => '1024k',
                    'mimeTypes' => [
                        'image/jpeg',
                        'image/png',
                    ],
                    'mimeTypesMessage' => 'Please upload a valid photo',
                ])
            ],
        ])
        ->add('Descrition')    
        ->add('Duration', ChoiceType::class, [
                'choices' => [
                    '0.5h' => '0.5h',
                    '1h' => '1h',
                    '2h' => '2h',
                    '3h' => '3h',
                   
                ],
            ])
            ->add('Difficulty', ChoiceType::class, [
                'choices' => [
                    'Débutant' => 'Débutant',
                    'intermédiaire' => 'intermédiaire',
                    'avancé' => 'avancé',
                   
                ],
            ])
            
            ->add('Category', ChoiceType::class, [
                'choices' => [
                    'Tourisme Durable' => 'Tourisme Durable',
                    'Sciences de la Nature' => 'Sciences de la Nature',
                    'Sciences de l"Environnement' => 'Sciences de l"Environnement',
                    'Arts visuels' => 'Arts visuels',
                ],
            ])
              
             
        ;
    }
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Course::class,
        ]);
    }
}