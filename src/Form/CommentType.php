<?php

// src/Form/CommentType.php

namespace App\Form;

use App\Entity\Comment;
use App\Entity\Forum;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class CommentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('content')
            ->add('img', FileType::class, [
                'label' => 'votre image  (JPEG file)',
                'constraints' => [
                    new File([
                        'maxSize' => '1024k',
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/jpg',
                        ],
                        'mimeTypesMessage' => 'Please upload a valid photo',
                    ])
                ],
                'data_class' => null, // Set data_class to null
            ])
            ->add('rate');
           
            }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Comment::class,
            'forum' => null,
        ]);
    }
}
