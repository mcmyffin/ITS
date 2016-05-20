<?php

/**
 * Created by PhpStorm.
 * User: dima
 * Date: 11.05.16
 * Time: 11:23
 */
class Time
{

    protected $timeInSec;

    public function __construct()
    {
        $this -> year   = date("Y");
        $this -> month  = date("n");
        $this -> day    = date("j");

        $this -> hour = date("H")+1;
        $this -> min  = date("i")+1;


        $this -> timeInSec =
            ($this->min  * 60)+
            ($this->hour * 60 * 60) +
            ($this->day  * 24 * 60 * 60) +
            ($this->month * 30 * 24 * 60 * 60) +
            ($this->year * 12 * 30 * 24 * 60 * 60);
    }


    public function getTimeInSec()
    {
        return $this->timeInSec;
    }

    public function getTimeInSecPlus30min()
    {
        $minInSec30 = 30*60;
        return $this->timeInSec + $minInSec30;
    }

}

?>