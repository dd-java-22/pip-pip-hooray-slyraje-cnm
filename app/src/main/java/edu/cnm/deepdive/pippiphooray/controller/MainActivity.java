package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.ActivityMainBinding;

/**
 * Main activity hosting the application's navigation graph and top-level UI.
 *
 * <p>This activity sets up edge-to-edge display, the toolbar, the bottom
 * navigation bar, and Navigation component integration.
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private AppBarConfiguration appBarConfiguration;

  /**
   * Initializes the activity UI and configures navigation behavior.
   *
   * @param savedInstanceState saved instance state, if any.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    EdgeToEdge.enable(this);
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment, (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(0, 0, 0, systemBars.bottom);
      return insets;
    });
    setSupportActionBar(binding.toolbar);
    NavHostFragment navHostFragment = (NavHostFragment)
        getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    NavController navController = navHostFragment.getNavController();
    appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.bottomNav, navController);
    navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
      if (destination.getId() == R.id.sign_in_fragment) {
        binding.bottomNav.setVisibility(View.GONE);
      } else {
        binding.bottomNav.setVisibility(View.VISIBLE);
      }
    });

  }

  /**
   * Handles Up navigation through the Navigation component.
   *
   * @return {@code true} if navigation was handled; otherwise {@code false}.
   */
  @Override
  public boolean onSupportNavigateUp() {
    NavHostFragment navHostFragment = (NavHostFragment)
        getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    NavController navController = navHostFragment.getNavController();
    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }

}