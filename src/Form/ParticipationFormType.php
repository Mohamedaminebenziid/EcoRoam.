<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints as Assert;

class ParticipationFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('name', TextType::class, [
                'label' => 'Name:',
                'required' => true,
                'constraints' => [
                    new Assert\NotBlank(),
                    new Assert\Length(['max' => 255]),
                ],
            ])
            
            ->add('id_card', TextType::class, [
                'label' => 'ID Card:',
                'required' => true,
                'constraints' => [
                    new Assert\NotBlank(),
                    new Assert\Type(['type' => 'numeric']),
                    new Assert\Length(['max' => 8, 'min' => 8]),
                    new Assert\Range([
                        'min' => 0,  // Minimum allowed value
                        'max' => 99999999,  // Maximum allowed value (8 digits)
                        'minMessage' => 'The value must be exactly {{ limit }} digits long.',
                        'maxMessage' => 'The value must be at most {{ limit }} digits long.',
                    ]),
                ],
            ])
            ->add('phone_number', TextType::class, [
                'label' => 'Phone Number:',
                'required' => true,
                'constraints' => [
                    new Assert\NotBlank(),
                    new Assert\Type(['type' => 'numeric']),
                    new Assert\Length(['max' => 8, 'min' => 8]),
                    new Assert\Range([
                        'min' => 0,  // Minimum allowed value
                        'max' => 99999999,  // Maximum allowed value (8 digits)
                        'minMessage' => 'The value must be exactly {{ limit }} digits long.',
                        'maxMessage' => 'The value must be at most {{ limit }} digits long.',
                    ]),
                ],
            ])
            ->add('submit', SubmitType::class, [
                'label' => 'Submit',
            ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
