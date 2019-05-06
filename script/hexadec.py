import os
import sys 
import csv
import re

#Creazione di script congiunto 
#modificare i path in base alla revisione 

def getFirstLetter(s):
    return s[0].capitalize()


def listaPresenti():
    lista=[]
    with open('presenti.txt', 'r') as presenti:
        lista = presenti.read().splitlines()
    return lista

def aggiungiVoce(titolo, descrizione):

    presenti = listaPresenti()

    if len(str(titolo)) == 0:
        print("--- Errore: titolo vuoto ---")
        exit()

    if titolo == 'q':
        print('End')
        exit()
    if str(titolo).lower() in presenti:
        print('--- Errore, termine '+titolo+' già presente ---')
        exit()
    if titolo[0].capitalize() not in 'QWERTYUIOPASDFGHJKLZXCVBNM':
        print('--- Shit, non abbiamo considerato le eventuali parole che '
              'cominciano con cose diverse da una lettera, sorry ---')
        exit()

        # metto la capitol letter
    if len(titolo) > 1:
        titolo = titolo[0].capitalize()+titolo[1:]
    else:
        titolo = titolo.capitalize()
    with open('../RR/Esterni/Glossario/sezioni/'+getFirstLetter(titolo)+'.tex', 'a') as file:
        if os.stat('../RR/Esterni/Glossario/sezioni/'+getFirstLetter(titolo)+'.tex').st_size == 0:
            file.write('\section{\\quad$'+getFirstLetter(titolo)+'\\quad$}\n')

        file.write('\subsection{'+titolo+'}\n')
        file.write('\index{' + titolo + '}\n')
        file.write(descrizione+'\n\n')

        with open('presenti.txt', 'a') as file2:
            file2.write(titolo.lower()+'\n')
            presenti.append(titolo.lower())

        print('--- Aggiunto '+titolo+' ---')

# Quando viene chiamato il comando python hexadec.py ho questo risultato

if len(sys.argv) == 1:
    print('Script usage: \n'
          '\t hexadec.py -a \t\t\t=> inserire a mano \n'
          '\t hexadec.py -f nomeFile.csv\t=> importa glossario da csv \n'
          '\t hexadec.py -g \t=> calcola gulpease')
    exit()


elif len(sys.argv) ==2 and sys.argv[1] == '-a':
    print('Inserimento a mano dei termini')
    while True:
        print('=> Inserire il nome del NUOVO TERMINE (\'q\' per uscire ):\n')
        titolo = input()
        print('=> inserisci la descrizione ')
        descrizione = input()
        aggiungiVoce(titolo, descrizione)

elif len(sys.argv) ==3 and sys.argv[1] == '-f':
    #lettura e scrittura da file csv
    print('Inserimento de termini nel glossario')
    #resetto presenti
    nuovofile = open('presenti.txt', 'w')
    for char in 'QWERTYUIOPASDFGHJKLZXCVBNM':
        nuovofile = open('../RR/Esterni/Glossario/sezioni/'+char+'.tex', 'w')
        nuovofile.close()
    nuovofile.close()

    types_of_encoding = ["utf8", "cp1252"]
    for encoding_type in types_of_encoding:
        with open(sys.argv[2],encoding = encoding_type, errors ='replace') as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            intestazione = True
            for dato in csv_reader:
                if intestazione:
                    print('Apertura del documento..')
                    intestazione = False
                else:
                    if dato[1] == "":
                        print(dato[0]+' non ha una descrizione! Impossibile proseguire')
                        exit()
                    aggiungiVoce(dato[0], dato[1])

            print('Finito')

elif len(sys.argv) ==2 and sys.argv[1] == '-g':
    print('calcola gulpease')
    rev = '../RR/'
    revisione ='Revisione dei Requisiti'
    rootE = 'Esterni'
    rootI ='Interni'
    docs =dict()

    #lo fillo
    docs['Glo'] = 'Glossario//standard_documento/sezioni/'
    docs['NdP'] = 'NormeDiProgetto/standard_documento/sezioni/'
    docs['SdF'] = 'studioDiFattibilita/standard_documento/sezioni/'
    docs['PdP'] = 'PianoDiProgetto/'
    docs['AdR'] = 'AnalisiDeiRequisiti//standard_documento/sezioni/'
    docs['1VI'] = 'Verbale_I_2016-12-10/'
    docs['2VI'] = 'Verbale_I_2016-12-19/'
    docs['1VE'] = 'Verbale_E_2016-12-17/'
    docs['PdQ'] = 'PianoDiQualifica/standard_documento/sezioni/'
    docs['SDK'] = 'AnalisiSDK/'
    docs['LdP'] = 'LetteraDiPresentazione/'

    print('+------------+--------------+---------+\n'
        +'+------------+--------------+---------+\n'
        +'\t'+revisione+
        '\n+------------+--------------+---------+\n'
        '| Documento | Valore       | Esito    |\n'
        '+-----------+--------------+----------+')

    
else:
    print("comando non riconosciuto")



