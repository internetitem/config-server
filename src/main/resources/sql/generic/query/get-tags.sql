SELECT
  v.SettingId,
  t.TagType,
  t.TagValue
FROM
  SettingValues v LEFT OUTER JOIN
  SettingVersion v1 ON v.VersionFrom = v1.VersionId LEFT OUTER JOIN
  SettingVersion v2 ON v.VersionTo = v2.VersionId LEFT OUTER JOIN
  SettingApplicationComponent sac ON v.ComponentId = sac.ComponentId JOIN
  SettingTag t ON
    v.SettingId = t.SettingId
WHERE
  :validAt BETWEEN v.ValidFrom AND v.ValidTo AND
  (v.ApplicationId IS NULL OR v.ApplicationId = :applicationId) AND
  (v.EnvironmentId IS NULL OR v.EnvironmentId = :environmentId) AND
  (sac.ApplicationId IS NULL OR sac.ApplicationId = :applicationId) AND
  (:versionId IS NULL OR (
  (v1.VersionId IS NULL OR :versionId >= v1.VersionId) AND
  (v2.VersionId IS NULL OR :versionId <= v2.VersionId)
  ))
ORDER BY
  v.SettingKey
