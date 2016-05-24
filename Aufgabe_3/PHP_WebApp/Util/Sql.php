<?php
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 10.05.16
 * Time: 17:31
 */
class Sql
{
    protected static
        $dbh;

    public static function isConnected()
    {
        return self::$dbh != null;
    }


    public static function connect($host, $username, $password, $database=null)
    {
        try{
            $database = ($database) ? ';dbname=' . $database : '';
            self::$dbh = new PDO('mysql:host=' . $host . $database, $username, $password);
            return;
        }catch(PDOException $e){
            throw new RuntimeException($e->getMessage());
        }
    }
    public static function close()
    {
        self::$dbh = null;
    }
    public static function exe($sql, $para=null)
    {
        $para_copy = $para;
        $stmt = self::$dbh->prepare($sql);
        $bind_para = ($para !== null
            and (strpos($sql, ' LIMIT :') !== false or strpos($sql, ' limit :') !== false)
        ) ? true : false;

        if($bind_para and is_array($para)){
            foreach($para as $key => &$val){
                if(is_string($val)){
                    $stmt->bindParam($key, $val, PDO::PARAM_STR);
                }
                elseif(is_bool($val)){
                    $stmt->bindParam($key, $val, PDO::PARAM_BOOL);
                }
                elseif(is_null($val)){
                    $stmt->bindParam($key, $val, PDO::PARAM_NULL);
                }
                elseif(is_numeric($val)){
                    $stmt->bindParam($key, $val, PDO::PARAM_INT);
                }
                else{ // PDO::PARAM_FLOAT does not exist. handle float as string
                    $stmt->bindParam($key, (string)$val, PDO::PARAM_STR);
                }
            }
            $para = null;
        }

        if(!$stmt->execute($para)){
            $err_info   = $stmt->errorInfo();
            $sql_state  = $err_info[0];
            $ecode      = $err_info[1];
            $emsg       = $err_info[2];

            $sql_state  = '(SQLSTATE: ' . $sql_state . ')';
            $ecode      = '(eCode: ' . $ecode . ')';
            $emsg       = 'eMessage: ' . $emsg;

            $error = $sql_state . ' ' . $emsg . ' ' . $ecode;

            $sql = preg_replace('/\s+/', ' ', $sql);

            $para_sring = '';
            if($para_copy){
                foreach($para_copy as $k => $v){
                    $para_sring .= ($para_sring === '') ? '' : '; ';
                    $para_sring .= ((strpos($k, ':') !== false) ? '' : ':') . $k . ' => ' . $v;
                }
            }

            $error .= 'query: ' . $sql . ' para: ' . $para_sring;
            throw new RuntimeException($error);
        }
        $result = null;
        while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            if($result === null){
                $result = array();
            }
            $result[] = $row;
        }
        $stmt = null;
        return $result;
    }
    public static function lastInsertId()
    {
        return self::$dbh->lastInsertId();
    }
}

?>