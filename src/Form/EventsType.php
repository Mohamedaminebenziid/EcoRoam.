<?php

namespace App\Form;

use App\Entity\Events;
use Symfony\Component\Form\FormError;use Symfony\Component\Form\FormEvents;
use App\Entity\Activities;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\DependencyInjection\ParameterBag\ParameterBagInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Validator\Constraints\Count;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\FormEvent;


use Symfony\Component\Validator\Constraints as Assert;


class EventsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('name', null, [
            
            'constraints' => [
                new Assert\NotBlank(),
                new Assert\Length(['max' => 255]),
            ],
        ])
        ->add('price', null, [
            
            'constraints' => [
                new Assert\NotBlank(),
                new Assert\Type(['type' => 'numeric']),
                new Assert\GreaterThanOrEqual(0),
            ],
        ])
            ->add('state', ChoiceType::class, [
                
                'choices' => [
                    'Ariana' => 'Ariana',
                    'Beja' => 'Beja',
                    'Ben Arous' => 'Ben Arous',
                    'Bizerte' => 'Bizerte',
                    'Gabes' => 'Gabes',
                    'Gafsa' => 'Gafsa',
                    'Jendouba' => 'Jendouba',
                    'Kairouan' => 'Kairouan',
                    'Kasserine' => 'Kasserine',
                    'Kebili' => 'Kebili',
                    'Kef' => 'Kef',
                    'Mahdia' => 'Mahdia',
                    'Manouba' => 'Manouba',
                    'Medenine' => 'Medenine',
                    'Monastir' => 'Monastir',
                    'Nabeul' => 'Nabeul',
                    'Sfax' => 'Sfax',
                    'Sidi Bouzid' => 'Sidi Bouzid',
                    'Siliana' => 'Siliana',
                    'Sousse' => 'Sousse',
                    'Tataouine' => 'Tataouine',
                    'Tozeur' => 'Tozeur',
                    'Tunis' => 'Tunis',
                    'Zaghouan' => 'Zaghouan',
                ],
                'placeholder' => 'Choose a governorate',
                'constraints' => [
                    new Assert\NotBlank(),
                ],
            ])
            ->add('img', FileType::class, [
                'label' => 'Your image (JPEG file)',
                'required' => true, // Ensure that the field is required
                'constraints' => [
                    new Assert\NotNull([ // Ensure that the file is not null
                        'message' => 'Please upload an image file.',
                    ]),
                    new Assert\File([
                        'maxSize' => '1024k',
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/png',
                        ],
                        'mimeTypesMessage' => 'Please upload a valid photo (JPEG or PNG).',
                    ]),
                ],
                'data_class' => null
            ])
            ->add('description', null, [
                
                'constraints' => [
                    new Assert\NotBlank(),
                    new Assert\Length(['max' => 1000]),
                ],
            ])
            ->add('events', EntityType::class, [
                
                'class' => Activities::class,
                'label' => 'Select activities',
                'multiple' => true,
                'choice_label' => 'name', // Set the property to display as the label
                'constraints' => [
                    new Count([
                        'min' => 1,
                        'minMessage' => 'Please select at least one activity.',
                    ]),
                ],
            ])
     ->add('date');
      
        
       
       
    }
    

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Events::class,
            

        ]);
    }
}
