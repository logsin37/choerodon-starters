package io.choerodon.liquibase.metadata;

import java.sql.SQLException;
import java.util.Map;

import io.choerodon.liquibase.metadata.dto.MetadataTable;

public interface IMetadataDriver {
    Map<String, MetadataTable> selectTables() throws SQLException;
}
