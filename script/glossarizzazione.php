<?php
$rev = ('../RR/'); //al cambio di revisione modificare questa variabile
$revisione = 'Revisione dei requisiti';//e questa

$rootE = 'Esterni/';
$rootI = 'Interni/';
/*
 * Elenco delle directory dei documenti
 */
 $docs = array(
   //'Glo' => 'Glossario/',
   //'NdP' => 'NormeDiProgetto/sezioni/',
   'SdF' => 'studioDiFattibilita/sezioni/'
   //'PdP' => 'PianoDiProgetto/sezioni/',
   //'AdR' => 'AnalisiDeiRequisiti/sezioni/',
   //'1VI' => 'Verbale_I_2016-12-10/', // primo verbale interno. 1=primo,V=verbale,I=interno
   //'2VI' => 'Verbale_I_2016-12-19/',
   //'1VE' => 'Verbale_E_2016-12-17/',
   //'PdQ' => 'PianoDiQualifica/sezioni/',
   //'SDK' => 'AnalisiSDK/',
   //'LdP' => 'LetteraDiPresentazione/'
 );
 $voci = fopen('./presenti.txt', 'r');
 $linesToIgnore = "/^((\\\section)|(\\\subsection)|(\\\subsubsection)|(\\\parola)|(\\\url)|(\\\modifica)|" .
                   "(\\\paragraph)|(\\\subparagraph)|(\\\caption)|(\\\url)|(\\\parola)|(\\\modifica)|" .
                   "(\\\myparagraph)|(\\\mysubparagraph)|(\\\\texttt)|(\\\parola)|(\\\url)|(\\\modifica)|" .
                   "(\\\\ref)|(\\\label)|(\\\def\\\input@path))/";


error_reporting(E_ERROR | E_PARSE); // non vengono stampati i warning

echo <<< EOF
+---------------------------------
    $revisione
+---------------+---------------------+
| Documento     | Esito               |
+---------------+---------------------+

EOF;

$glo=false;
foreach ($docs as $doc => $dir) {
  if (file_exists($rev . $rootE . $dir)) $root = $rootE;
  else{
  	if (file_exists($rev . $rootI . $dir)) $root = $rootI;
    $files = scandir($rev . $root . $dir);
    foreach ($files as $file) {
      if (preg_match('/.tex$/', $file)) {
        $path = $rev . $root . $dir . $file;
        if (file_exists($path)) {
         echo $path;
         $filename=$path;
         $file =fopen($filename,'r');
        foreach($voci as $voce){
            $lineNumber = 0;
            rewind($file);
            foreach($file as $line){        
              $lineNumber++;
              $lineE=explode("\n", $line);
              $explodingLine= preg_grep($linesToIgnore,$lineE );
              if (preg_match("/\b$voce\b/", $line) && $voce!="") {
                if (empty($explodingLine)) { // glo
                  if($voce=="casi d'uso" || $voce=="Casi d'uso"){
                    $Vvoce=$voce;
                    $file_contents = file_get_contents($filename);
                    $file_contents = str_replace($Vvoce,"casi duso",$file_contents);
                    file_put_contents($filename,$file_contents);
                    $voce="casi duso";
                  }
                  $file_contents = file_get_contents($filename);
                  $file_contents = preg_replace("/$voce/","\glo{".$voce."}",$file_contents,1);
                  file_put_contents($filename,$file_contents);
                  $glo=true;
                  if($voce=="casi duso"){
                    $file_contents = file_get_contents($filename);
                    $file_contents = str_replace("casi duso",$Vvoce,$file_contents);
                    file_put_contents($filename,$file_contents);
                    $voce=$Vvoce;
                  }
                }
              }
            }
          }
          fclose($file);
            


        }
      }
    }
    if ($glo) {
      echo "| $doc      |  Glossariato            |\n";
      echo "+-----------+-------------------------+\n";
    }
    else {
      echo "| $doc      | Nessun file o directory |\n";
      echo "+-----------+-------------------------+\n";
    } 
    }
    fclose($voci);
}

echo "\n";
?>
