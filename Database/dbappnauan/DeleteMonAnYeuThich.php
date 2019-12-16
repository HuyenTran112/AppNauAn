<?php
require 'dbConnect.php';
global $connection;

//response array

        $mamonan = $_POST['mamonan'];
        $manguoidung = $_POST['manguoidung'];    
        $sql = "delete from monanyeuthich where mamonan='$mamonan' and manguoidung='$manguoidung'";          
    
            	if(mysqli_query($connection,$sql)){
                //filling response array with values
                    echo "success";              	
            	}
                else echo "error";
            //if some error occurred
        	

?>