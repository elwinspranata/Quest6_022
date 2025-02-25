package com.example.navigasidengandata.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navigasidengandata.ui.view.screen.MahasiswaFormView
import com.example.navigasidengandata.ui.view.screen.RencanaStudyView
import com.example.navigasidengandata.ui.view.screen.SplashView
import com.example.navigasidengandata.ui.view.viewmodel.MahasiswaViewModel
import com.example.navigasidengandata.ui.view.viewmodel.RencanaStudyViewModel
import com.example.praktikum6.ui.view.screen.DetailMahasiswaView

enum class Halaman {
    Splash,
    Mahasiswa,
    Matakuliah,
    Tampil
}

@Composable
fun Mahasiswa(
    modifier: Modifier = Modifier,
    mahasiswaViewModel: MahasiswaViewModel = viewModel(),
    krsViewModel: RencanaStudyViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
)
{
    val mahasiswaUiState = mahasiswaViewModel.mhsStateUi.collectAsState().value
    val krsUiState = krsViewModel.krsStateUi.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Halaman.Splash.name,
        modifier = modifier.padding()
    ){
        composable(route = Halaman.Splash.name){
            SplashView {
                navController.navigate(Halaman.Mahasiswa.name)
            }
        }
        composable(route = Halaman.Mahasiswa.name)
        {
            MahasiswaFormView(modifier = Modifier,
                onSubmitButtonClicked = {
                    mahasiswaViewModel.saveDataMhs(it)
                    navController.navigate(Halaman.Matakuliah.name)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.Matakuliah.name){
            RencanaStudyView(
                mahasiswa = mahasiswaUiState,
                onSubmitButtonClicked = { krsViewModel.saveDataKRS(it)},
                onBackButtonClicked = { navController.popBackStack()}
            )
        }
        composable(route = Halaman.Tampil.name){
            DetailMahasiswaView(
                dataMhs = mahasiswaUiState,
                DataKRS = krsUiState,
                onClickButton = {
                    navController.popBackStack()
                }
            )
        }
    }
}