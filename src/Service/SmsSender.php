<?php
namespace App\Service;

use ClickSend\Configuration;
use ClickSend\Api\SMSApi;
use ClickSend\Model\SmsMessage;
use ClickSend\Model\SmsMessageCollection;
use GuzzleHttp\ClientInterface;

class SmsSender
{
    private $api;
    private $apiKey;

    public function __construct(string $apiKey)
    {
        $config = Configuration::getDefaultConfiguration()
            ->setUsername('ALAEDDINE.SALAH@esprit.tn') // Replace 'YOUR_CLICKSEND_USERNAME' with your actual ClickSend username
            ->setPassword($apiKey);

        $this->api = new SMSApi(new \GuzzleHttp\Client(), $config);
        $this->apiKey = $apiKey;
    }

    public function sendSms(string $phoneNumber, string $message): void
    {
        $msg = new SmsMessage();
        $msg->setBody($message);
        $msg->setTo($phoneNumber);

        $smsMessages = new SmsMessageCollection();
        $smsMessages->setMessages([$msg]);

        try {
            $this->api->smsSendPost($smsMessages);
        } catch (\Exception $e) {
            throw new \RuntimeException('Failed to send SMS: ' . $e->getMessage());
        }
    }
}