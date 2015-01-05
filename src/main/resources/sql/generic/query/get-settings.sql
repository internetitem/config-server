SELECT
  v.SettingId,
  v.SettingKey,
  v.SettingValue,
  v.ValidFrom,
  v.ValidTo,
  v1.VersionString AS FromVersion,
  v1.Ordering AS FromVersionOrder,
  v1.VersionId AS FromVersionId,
  v2.VersionString AS ToVersion,
  v2.Ordering AS ToVersionOrder,
  v2.VersionId AS ToVersionId,
  c.ComponentName AS ComponentName,
  c.ComponentId AS ComponentId,
  sac.Ordering AS ComponentOrder,
  a.ApplicationName AS ApplicationName,
  a.ApplicationId AS ApplicationId,
  e.EnvironmentName AS EnvironmentName,
  e.EnvironmentId AS EnvironmentId,
  v.LastModifiedTs
FROM
  SettingValues v LEFT OUTER JOIN
  SettingVersion v1 ON v.VersionFrom = v1.VersionId LEFT OUTER JOIN
  SettingVersion v2 ON v.VersionTo = v2.VersionId LEFT OUTER JOIN
  SettingApplicationComponent sac ON v.ComponentId = sac.ComponentId LEFT OUTER JOIN
  SettingComponent c ON sac.ComponentId = c.ComponentId LEFT OUTER JOIN
  SettingApplication a ON v.ApplicationId = a.ApplicationId LEFT OUTER JOIN
  SettingEnvironment e ON v.EnvironmentId = e.EnvironmentId
WHERE
  :validAt BETWEEN v.ValidFrom AND v.ValidTo AND
  (v.ApplicationId IS NULL OR v.ApplicationId = :applicationId) AND
  (v.EnvironmentId IS NULL OR v.EnvironmentId = :environmentId) AND
  (sac.ApplicationId IS NULL OR sac.ApplicationId = :applicationId) AND
  (:versionId IS NULL OR (
    (v1.VersionId IS NULL OR :versionId >= v1.VersionId) AND
    (v2.VersionId IS NULL OR :versionId <= v2.VersionId)
  ))
