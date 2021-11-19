// imports
const {promisify} = require('util');
const {resolve} = require('path');
const fs = require('fs');
const readdir = promisify(fs.readdir);
const stat = promisify(fs.stat);

// constantes
const baseEntity = "Example"
const basePackage = "com/xbidi/spring";

// nombres para reemplazar
const entity = process.argv.slice(2)[0];
const fields = [];
let fieldCounter = 1;
while (process.argv.slice(2)[fieldCounter] != null && process.argv.slice(2)[fieldCounter + 1] != null) {
    let type = process.argv.slice(2)[fieldCounter];
    let name = process.argv.slice(2)[fieldCounter + 1];
    fields.push(`protected ${type} ${name};`);
    fieldCounter += 2;
}
let fieldsString = fields.join('\n');

const entityLowerCase = entity.toLowerCase();
const entityCamelCase = toCamelCase(entity);
const entityCamelCaseFirsUpperCase = toUpperFistLetter(entityCamelCase);
const entityUpperSnakeCase = toUpperSnakeCase(entity);

// directorios para trabajar
const currentDir = __dirname;
const filesDir = `${currentDir}/src/main/java/${basePackage}/content/example`;
const filesDestinationDir = `${currentDir}/src/main/java/${basePackage}/content/${entityLowerCase}`;
const testsDir = `${currentDir}/src/test/java/${basePackage}/content/example`;
const testsDestinationDir = `${currentDir}/src/test/java/${basePackage}/content/${entityLowerCase}`;

// copia los ficheros originales al nuevo directorio
copyFiles(filesDir, filesDestinationDir).then(r => {/**/
});

copyFiles(testsDir, testsDestinationDir).then(r => {/**/
});

function replaceEntityNames(string) {
    let response = string;
    response = response.replaceAll(baseEntity.toLowerCase(), entityLowerCase)
    response = response.replaceAll(baseEntity, entityCamelCaseFirsUpperCase)
    response = response.replaceAll(toUpperSnakeCase(baseEntity), entityUpperSnakeCase)
    return response.replaceAll("/* FIELDS */", fieldsString)
}

async function copyFiles(originalDir, destinationDir) {
    getFiles(originalDir).then(files => {
        for (let file of files) {
            const destinationFile = replaceEntityNames(file.replace(originalDir, destinationDir));
            const destinationFileDir = destinationFile.substring(0, destinationFile.lastIndexOf("/") + 1);
            fs.mkdirSync(destinationFileDir, {recursive: true});
            const content = replaceEntityNames(fs.readFileSync(file, 'utf8'));
            if (fs.existsSync(destinationFile)) fs.unlinkSync(destinationFile)
            fs.writeFileSync(destinationFile, content);
        }
    });
}

async function getFiles(dir) {
    const subDirs = await readdir(dir);
    const files = await Promise.all(subDirs.map(async (subDir) => {
        const res = resolve(dir, subDir);
        return (await stat(res)).isDirectory() ? getFiles(res) : res;
    }));
    return files.reduce((a, f) => a.concat(f), []);
}


function toCamelCase(str) {
    return str.replace(/^\w|[A-Z]|\b\w/g, function (word, index) {
        return index === 0 ? word.toLowerCase() : word.toUpperCase();
    }).replace(/\s+/g, '');
}

function toUpperSnakeCase(string) {
    return string.replace(/[A-Z]/g, letter => `_${letter.toLowerCase()}`).toUpperCase();
}

function toUpperFistLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
