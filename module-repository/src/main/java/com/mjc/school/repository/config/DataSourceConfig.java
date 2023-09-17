package com.mjc.school.repository.config;

import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.PropertiesReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DataSourceConfig {
    @Bean
    PropertiesReader propertiesReader() throws IOException {
        return new PropertiesReader();
    }

    @Bean
    DataSource<AuthorModel> authorModelDataSource() throws IOException {
        String authorFileName = propertiesReader().getProperties().getProperty(PropertiesName.AUTHOR_FILE);
        return new DataSource<>(authorFileName, AuthorModel.class);
    }

    @Bean
    DataSource<NewsModel> newsModelDataSource() throws IOException {
        String newsFileName = propertiesReader().getProperties().getProperty(PropertiesName.NEWS_FILE);
        return new DataSource<>(newsFileName, NewsModel.class);
    }
}
