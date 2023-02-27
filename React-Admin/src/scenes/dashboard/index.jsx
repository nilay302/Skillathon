import { Box } from "@mui/system";
import { useEffect } from "react";
import Header from "../../components/Header";

const Dashboard = () =>{
    useEffect(()=>{
        if(localStorage.getItem('token') == '1')window.location = '/login';
    },[])
    return (
    <Box m="20px">
        <Box display="flex" justifyContent="space-between" alignItems="center">
            <Header title="DASHBOARD" subtitle = "Welcome to your Dashboard" />
        </Box>
    </Box>)
}
export default Dashboard;