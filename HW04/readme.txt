Problems:

Task1: sometimes gets stuck at local minima with value ~41.

Some params:
        args = new String[] { "100", "0.05", "100000", "rouletteWheel", "0.1" };
        args = new String[] { "100", "0.05", "100000", "tournament:5", "0.015" };
        
        
Task2:
Pretpostavimo da su stapovi indeksirani.

Jedno rjesenje sam prestavio kao permutaciju stapova.
Dekoder interpretira rjesenje kao permutaciju stapova te ih redom slaze u kutiju po stupcima. 
Kao vrijednosti vraca udio praznog mjesta po stupcima.
Fitnes mjera je negativni srednji udio praznog mjesta po stupcima.


Ideja bila problem prestaviti kao bilo koji drugi problem permutiranog niza cijelih brojeva (pritom donekle mozda zakomplicirao stvar).

Koliko sam stigao testirati, rjesenje ili nade optimalno ili 
zapne na rjesenju za 1 udaljenom od optimalnog.
Potrebno implementirati jos koje krizanje (PMX, PBX i ULX) i vidjet hoce li biti ista bolje.

