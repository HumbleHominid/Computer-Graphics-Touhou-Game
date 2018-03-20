const fs = require('fs');

// write blank file
fs.writeFileSync('srs.md', '');

// add title
fs.appendFileSync('srs.md', '# Touhou Fan Game SRS\n\n');

// add actors
fs.appendFileSync('srs.md', '## Actors\n');
fs.appendFileSync('srs.md', fs.readFileSync('actors.md'));

// Hard coded search through `Use_Cases` dir for now
let caseData = JSON.parse(fs.readFileSync('Use_Cases/.build'));

fs.appendFileSync('srs.md', '\n## Use Cases\n\n');

caseData.forEach((useCase) => {
    let fileData = fs.readFileSync(`Use_Cases/${useCase.file}`);

    fs.appendFileSync('srs.md', `### ${useCase.name}\n${fileData}\n`);
});
