
<?php

error_reporting(E_ERROR | E_PARSE); // non vengono stampati i warning

$rev =('../RR/');// ('../RA/'); //al cambio di revisione modificare questa variabile
$revisione = 'RR';//e questa
$glossarizzato=false;
$rootE = 'Esterni/';//'Esterno/';
$rootI = 'Interni/';//'Interno/';
/*
 * Elenco delle directory dei documenti
 */

$docs = array(
  /*'Glo' => 'Glossario/sezioni/',
  'NdP' => 'NormeDiProgetto/sezioni/',
  'PdP' => 'PianoDiProgetto/',
  'AdR' => 'AnalisiDeiRequisiti/sezioni/',
  'PdQ' => 'PianoDiQualifica/sezioni',*/
  //'DdP' => 'DefinizioneDiProdotto/sezioni',
  //'LdP' => 'LetteraDiPresentazione/sezioni'
  'SdF' => 'studioDiFattibilita/sezioni/'
);
echo <<< EOF

###  Software sotto licenza MIT ###

______                            _
|  __ \ | 
| |  \/ | ___  ___ ___  __ _ _ __ _ _______
| | __| |/ _ \/ __/ __|/ _' | '__| |_  / _ \
| |_\ \ | (_) \__ \__ \ (_| | |  | |/ /  __/
 \____/_|\___/|___/___/\__,_|_|  |_/___\___|

  Improved by Andrea Chinello     v3.0

EOF;

echo <<< EOF
+-----------------------+---------------------+
 $revisione
+-----------------------+---------------------+
|       Documento       |        Esito        |
+-----------------------+---------------------+

EOF;


function glossarizeDoc($path) {
  if (isset($path)) {
    $docSign = $path;  /*
  }
  /**
   * Apertura del documento e dell'elenco delle voci di glossario
   */
  $filename = $path;
  $file = fopen($filename, 'r');
  $voci = fopen('presenti.txt', 'r');

  /**
   * Regex delle righe da ignorare. 
  */

   $linesToIgnore = "/^((\\\section)|(\\\subsection)|(\\\subsubsection)|(\\\parola)|(\\\url)|(\\\modifica)|" .
                    "(\\\paragraph)|(\\\subparagraph)|(\\\caption)|(\\\url)|(\\\parola)|(\\\modifica)|" .
                    "(\\\myparagraph)|(\\\mysubparagraph)|(\\\\texttt)|(\\\parola)|(\\\url)|(\\\modifica)|" .
                    "(\\\\ref)|(\\\label)|(\\\def\\\input@path))/";

  /**
   * Per ogni voce di glossario...
   */
  while (!feof($voci)) {
    $glo_or_deglo=$_SERVER[ "argv" ][1];
    $voce =trim(fgets($voci));
    $lineNumber = 0;
    rewind($file);

    /*scorre il documento in cerca di essa*/
    while (!feof($file)) {

      $line = fgets($file);
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
          ///* Attivare qui per GLOSSARIZZARE ----

          if($glo_or_deglo!="-d"){
              $file_contents = file_get_contents($filename);
              $file_contents = preg_replace("/$voce/","\glo{".$voce."}",$file_contents,1);
              file_put_contents($filename,$file_contents);
          }
          else{
              $file_contents = file_get_contents($filename);
              $file_contents = str_replace("\glo{".$voce."}","$voce",$file_contents);
              file_put_contents($filename,$file_contents);
          }
          if($voce=="casi duso"){
            $file_contents = file_get_contents($filename);
            $file_contents = str_replace("casi duso",$Vvoce,$file_contents);
            file_put_contents($filename,$file_contents);
            $voce=$Vvoce;
          }
          if($glo_or_deglo!="-d") break;
        
        } else { // deglo
        }
      }
    }
  }
  fclose($file);
}

 //echo "Glossarizzazione di: \n";

foreach ($docs as $doc => $dir) {
  if (file_exists($rev . $rootE . $dir)) $root = $rootE;
  else{
  	if (file_exists($rev . $rootI . $dir)) $root = $rootI;
    $files = scandir($rev . $root . $dir);
    foreach ($files as $file) {
    	// echo "\n$file\n";
      if (preg_match('/.tex$/', $file)) {
        $path = $rev . $root . $dir . $file;
        if (file_exists($path)) {
          // qui si glossarizza
            $correggilatex = file_get_contents($path);
            $correggilatex = str_replace("\\\\glo{LaTeX}","\\LaTeX{}",$correggilatex);
            file_put_contents($path,$correggilatex);
            glossarizeDoc($path); 
        }
      }
    }
    if($_SERVER[ "argv" ][1]!="-d") $phrases="Glossarizzato";
    else $phrases="DE-Glossarizzato";
    if(file_exists($rev.$root.$dir)){
      echo "|        $doc            |    \033[33;32m $phrases";echo"\e[0m   |   \n";
      echo "+-----------------------+---------------------+\n";
    }else{
      echo "|        $doc            |    \033[33;31m Non esistente";echo"\e[0m   |    \n";
      echo "+-----------------------+---------------------+\n";
    }
  }
}
}
?>