<?php
require 'dbConnect.php';
global $connection;

 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    //checking the required parameters from the request
    if(isset($_POST['mamonan']) and isset($_POST['tenmonan']) and isset($_POST['nguyenlieu']) and isset($_POST['congthuc']) and isset($_POST['maloaimonan']))
    {
        $mamonan = (int)$_POST['mamonan'];
        $tenmonan = $_POST['tenmonan'];
        $nguyenlieu=$_POST['nguyenlieu'];
        $congthuc=$_POST['congthuc'];
        $maloaimonan=$_POST['maloaimonan'];
        $strpath = $_POST['strpath'];

        if($strpath == "http"){          
                $sql = "UPDATE monan SET tenmonan = '$tenmonan', nguyenlieu = '$nguyenlieu', congthuc = '$congthuc', maloaimonan = $maloaimonan WHERE mamonan = $mamonan";          
    
                if(mysqli_query($connection,$sql)){
                //filling response array with values
                    echo "success";                 
                }
                else echo "error";           
        }
        else{
            $upload_path = 'imagemonan/'; //this is our upload folder
            $server_ip = "172.17.20.139:8080/"; //Getting the server ip
            $upload_url = 'http://'.$server_ip.'AppNauAn/Database/dbappnauan/'.$upload_path;
            //response array
            $response = array();



            $fileinfo = pathinfo($_FILES['image']['name']);//getting file info from the request
            $extension = $fileinfo['extension']; //getting the file extension
            $file_url = $upload_url . getFileName() . '.' . $extension; //file url to store in the database
            $file_path = $upload_path . getFileName() . '.'. $extension; //file path to upload in the server
        
            try{
                move_uploaded_file($_FILES['image']['tmp_name'],$file_path); //saving the file to the uploads folder;          
          
                $sql = "UPDATE monan SET tenmonan = '$tenmonan', nguyenlieu = '$nguyenlieu', congthuc = '$congthuc', hinhanh = '$file_url', maloaimonan = $maloaimonan WHERE mamonan = $mamonan";           
    
                if(mysqli_query($connection,$sql)){
                //filling response array with values
                    $response['error'] = false;
                    $response['mamonan'] = $mamonan;
                    $response['tenmonan'] = $tenmonan;
                    $response['nguyenlieu'] = $nguyenlieu;
                    $response['congthuc'] = $congthuc;
                    $response['hinhanh']=$file_url;
                    $response['maloaimonan']=$maloaimonan;
                    
                }
            //if some error occurred
            }catch(Exception $e){
                $response['error']=true;
                $response['message']=$e->getMessage();
            }
        }
        
        //displaying the response
        
    }else{
        if($strpath == "http"){
            echo "error"; 
        }else{
            $response['error'] = true;
            $response['message']='Please choose a file';
        }
        
    }
}

function getFileName(){
    global $connection;
     
    $sql = "SELECT max(mamonan) as mamonan FROM monan";
    $result = mysqli_fetch_array(mysqli_query($connection, $sql));
 
    if($result['mamonan']== null)
        return 1;
    else
        return ++$result['mamonan'];
     
    mysqli_close($connection);
}

?>