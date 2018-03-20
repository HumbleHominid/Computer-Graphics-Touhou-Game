const fs = require('fs');
const outfile = 'README.md';

function append(data = '') {
    fs.appendFileSync(outfile, data);
}

function hline() {
    append('---\n\n');
}

// write blank file
fs.writeFileSync(outfile, '');

// add title
append(fs.readFileSync('title.md'));
hline();

// add actors
append(fs.readFileSync('actors.md'));
hline();

// Hard coded search through `Use_Cases` dir for now
let caseData = JSON.parse(fs.readFileSync('Use_Cases/.build'));

append('## Use Cases\n\n');

caseData.forEach((useCase) => {
    let fileData = fs.readFileSync(`Use_Cases/${useCase.file}`);

    append(`### ${useCase.name}\n${fileData}\n`);
});
