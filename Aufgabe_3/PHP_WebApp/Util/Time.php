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

        $this -> hour = date("H");
        $this -> min  = date("i");


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

    /**
     * @return bool|string
     */
    public function getDay()
    {
        return $this->day;
    }

    /**
     * @return bool|string
     */
    public function getHour()
    {
        return $this->hour;
    }

    /**
     * @return bool|string
     */
    public function getMin()
    {
        return $this->min;
    }

    /**
     * @return bool|string
     */
    public function getMonth()
    {
        return $this->month;
    }

    /**
     * @return bool|string
     */
    public function getYear()
    {
        return $this->year;
    }



}

?>