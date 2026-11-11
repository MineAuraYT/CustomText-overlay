$ErrorActionPreference = 'Stop'
gradle build
if ($LASTEXITCODE -ne 0) { throw 'Gradle build failed. See the compiler output above.' }
$jar = Get-ChildItem -Path '.\build\libs' -Filter '*.jar' | Where-Object { $_.Name -notmatch '-sources|-dev' } | Select-Object -First 1
if ($null -eq $jar) { throw 'Could not find the built mod JAR in build\libs.' }
New-Item -ItemType Directory -Force -Path '.\JAR' | Out-Null
Copy-Item $jar.FullName '.\JAR\' -Force
Write-Host "Built mod: .\JAR\$($jar.Name)" -ForegroundColor Green
