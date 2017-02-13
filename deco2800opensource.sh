function gitdiffname() {
	git log --pretty="%H" --author=$1 |
	   	while read commit_hash
	    	do
	        	git show --oneline --name-only $commit_hash | tail -n+2
	    	done | sort | uniq
}

function gitBlameName() {
	gitdiffname $1 > files.txt

	filename="files.txt"
	while read -r line
	do
		if [ -f $line ]; then
			echo "======" $line "======" >> output.txt
			git blame $line | grep $1 | grep -vw "fatal" >> output.txt
		fi
	done < "$filename"

	rm files.txt
}

rm output.txt
filename="names.txt"
while read -r line
do
	name="$line"
	echo "Checking" $name
	gitBlameName $name
done < "$filename"
