const fs = require('fs');
const outfile = 'README.md';

function append(data = '') {
    fs.appendFileSync(outfile, data);
}

// write blank file
fs.writeFileSync(outfile, '');

// add title
append('# Touhou Fan Game SRS\n\n');

// add actors
append('## Actors\n');
append(fs.readFileSync('actors.md'));

// Hard coded search through `Use_Cases` dir for now
let caseData = JSON.parse(fs.readFileSync('Use_Cases/.build'));

append('\n## Use Cases\n\n');

caseData.forEach((useCase) => {
    let fileData = fs.readFileSync(`Use_Cases/${useCase.file}`);

    append(`### ${useCase.name}\n${fileData}\n`);
});
