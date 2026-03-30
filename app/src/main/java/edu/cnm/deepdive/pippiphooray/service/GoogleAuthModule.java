//package edu.cnm.deepdive.pippiphooray.service;
//
//import dagger.Binds;
//import dagger.Module;
//import dagger.hilt.InstallIn;
//import dagger.hilt.components.SingletonComponent;
//import edu.cnm.deepdive.pippiphooray.service.repository.GoogleAuthRepository;
//import edu.cnm.deepdive.pippiphooray.service.repository.GoogleAuthRepositoryImpl;
//import javax.inject.Singleton;
//
//@Module
//@InstallIn(SingletonComponent.class)
//public abstract class GoogleAuthModule {
//
//  @Binds
//  @Singleton
//  public abstract GoogleAuthRepository bindGoogleAuthRepository(
//      GoogleAuthRepositoryImpl impl);
//
//}