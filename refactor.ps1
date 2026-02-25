$managerPages = @("AddCustomerPage.java", "CustomersPage.java", "ManagerPage.java", "OpenAccountPage.java")
$customerPages = @("CustomerAccountPage.java", "CustomerDepositPage.java", "CustomerLoginPage.java", "CustomerTransactionsPage.java", "CustomerWithdrawlPage.java")

$files = Get-ChildItem -Path "src" -Filter "*.java" -Recurse

foreach ($f in $files) {
    if ($f.Length -eq 0) { continue }
    $content = [IO.File]::ReadAllText($f.FullName)
    $modified = $false

    if ($f.DirectoryName -match "pages\\manager$") {
        if ($content -match "package pages;") {
            $content = $content -replace "package pages;", "package pages.manager;"
            $modified = $true
        }
    }
    if ($f.DirectoryName -match "pages\\customer$") {
        if ($content -match "package pages;") {
            $content = $content -replace "package pages;", "package pages.customer;"
            $modified = $true
        }
    }

    foreach ($m in $managerPages) {
        $cls = $m.Replace(".java", "")
        if ($content -match "import pages\.$cls;") {
            $content = $content -replace "import pages\.$cls;", "import pages.manager.$cls;"
            $modified = $true
        }
    }
    foreach ($c in $customerPages) {
        $cls = $c.Replace(".java", "")
        if ($content -match "import pages\.$cls;") {
            $content = $content -replace "import pages\.$cls;", "import pages.customer.$cls;"
            $modified = $true
        }
    }

    # Inject imports for files that used them without importing because they were in the same package
    foreach ($m in $managerPages) {
        $cls = $m.Replace(".java", "")
        if ($content -match "\b$cls\b") {
            # if we are not in package manager, we need import manager.cls
            if (-not ($f.DirectoryName -match "pages\\manager$") -and -not ($content -match "import pages\.manager\.$cls;")) {
                $content = $content -replace "(package [a-zA-Z0-9_\.]+;)", "`$1`r`nimport pages.manager.$cls;"
                $modified = $true
            }
        }
    }
    foreach ($c in $customerPages) {
        $cls = $c.Replace(".java", "")
        if ($content -match "\b$cls\b") {
            if (-not ($f.DirectoryName -match "pages\\customer$") -and -not ($content -match "import pages\.customer\.$cls;")) {
                $content = $content -replace "(package [a-zA-Z0-9_\.]+;)", "`$1`r`nimport pages.customer.$cls;"
                $modified = $true
            }
        }
    }

    if ($modified) {
        [IO.File]::WriteAllText($f.FullName, $content, [Text.Encoding]::UTF8)
        Write-Host "Modified $($f.Name)"
    }
}
Write-Host "Done refactoring"
