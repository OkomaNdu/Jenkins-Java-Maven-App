def buildApp() {
    echo 'build the application...'
}

def testApp() {
    echo 'testing the application...'
}

def deployApp() {
    echo 'build the application...'
    echo "deploying version ${params.VERSION}"
}
return this