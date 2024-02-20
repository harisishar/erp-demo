<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Example_gateway extends App_gateway
{
    public function __construct()
    {
        /**
         * Call App_gateway __construct function
         */
        parent::__construct();

        /**
         * Gateway unique id - REQUIRED
         * 
         * * The ID must be alphanumeric
         * * The filename (Example_gateway.php) and the class name must contain the id as ID_gateway
         * * In this case our id is "example"
         * * Filename will be Example_gateway.php (first letter is uppercase)
         * * Class name will be Example_gateway (first letter is uppercase)
         */
        $this->setId('Billplz_gateway');

        /**
         * REQUIRED
         * Gateway name
         */
        $this->setName('Billplz_gateway');

        /**
         * Add gateway settings
         * You can add other settings here to fit for your gateway requirements
         *
         * Currently only 2 field types are accepted for gateway
         *
         * 'type'=>'yes_no'
         * 'type'=>'input'
         */
        $this->setSettings(array(
            array(
                'name' => 'api_secret_key',
                'encrypted' => true,
                'label' => ' https://www.billplz.com/api/v3/collections',
                'type' => 'input',
            ),
            array(
                'name' => 'api_publishable_key',
                'label' => '72a4701e-688b-41c7-a190-6c115ffc605b',
                'type' => 'input'
            ),
            array(
                'name' => 'currencies',
                'label' => 'settings_paymentmethod_currencies',
                'default_value' => 'MYR'
            ),
        ));

        /**
         * REQUIRED
         * Hook gateway with other online payment modes
         */
        hooks()->add_filter('app_payment_gateways', [$this, 'initMode']);
    }

    /**
     * Each time a customer click PAY NOW button on the invoice HTML area, the script will process the payment via this function.
     * You can show forms here, redirect to gateway website, redirect to Codeigniter controller etc..
     * @param  array $data - Contains the total amount to pay and the invoice information
     * @return mixed
     */
    public function process_payment($data)
    {
        var_dump($data);
        die;
    }
}
