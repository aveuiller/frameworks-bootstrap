./gradlew sonarqube \
  -Dsonar.host.url="$SONAR_HOST_URL" \
  -Dsonar.qualitygate.wait=true \
  -Dsonar.login="$SONAR_TOKEN"
