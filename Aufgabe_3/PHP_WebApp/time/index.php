<?php
include '../Util/Time.php';
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 24.05.16
 * Time: 08:35
 */

 $currentTime = NEW Time();
 header("Content-Type:application/json",true,200);

 echo '{"time":"'.$currentTime->getTimeInSec().'","year":"'.$currentTime->getYear().'","month":"'.$currentTime->getMonth().'","day":"'.$currentTime->getDay().'","hour":"'.$currentTime->getHour().'","min":"'.$currentTime->getMin().'"}';

?>