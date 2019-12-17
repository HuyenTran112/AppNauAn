<?php
require 'dbConnect.php';
global $connection;

//response array

        $mamonan = $_POST['mamonan'];
        $manguoidung = $_POST['manguoidung'];    
        $sql = "insert monanyeuthich values('$mamonan','$manguoidung')";          
    
            	if(mysqli_query($connection,$sql)){
                //filling response array with values
                    echo "success";              	
            	}
                else echo "error";
            //if some error occurred
        	

?>