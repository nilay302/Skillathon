import { Box } from "@mui/material";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { tokens } from "../../theme";
import Header from "../../components/Header";
import { useTheme } from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios";


const Scholarships = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  const columns = [
    { field: "_id", headerName: "ID", flex: 1 },
    {
      field: "name",
      headerName: "Scholarship Name",
      flex: 1,
      cellClassName: "name-column--cell",
    },
    {
      field: "amount",
      headerName: "Amount",
      type: "number",
      flex: 1,
      headerAlign: "left",
      align: "left",
    },
    {
        field: "noOfAppliedUsers",
        headerName: "Applied Users",
        type: "number",
        flex: 1,
        headerAlign: "left",
        align: "left",
      },
      {
        field: "noOfAcceptedUsers",
        headerName: "Accepted Users",
        type: "number",
        flex: 1,
        headerAlign: "left",
        align: "left",
      },
      {
        field: "noOfRejectedUsers",
        headerName: "Rejected Users",
        type: "number",
        flex: 1,
        headerAlign: "left",
        align: "left",
      },
   
  ];

  const [rowData, getRowData] = useState([]);
  useEffect(() => {
    const URL = `https://bap-production.up.railway.app/admin/scholarships`;
    axios
      .get(URL, 
        {
        headers: {
          "Content-Type": "application/json",
          "authorization": `Bearer ${localStorage.getItem('token')}`,
        },
      })
      .then((response) => {
        // console.log(response);
        if (response.status === 200) {
          // console.log(response.data.content);
          getRowData(response.data.content);
        } else {
          alert("Some error occurred.");
          window.location = "/login";
          return;
        }
      })
      .catch((err) => {
        alert(err);
        window.location = "/login";
        return;
      });
  }, []);


  return (
    <Box m="20px">
      <Header
        title="SCHOLARSHIPS"
        subtitle="List of all Scholarships available"
      />
      <Box
        m="40px 0 0 0"
        height="75vh"
        sx={{
          "& .MuiDataGrid-root": {
            border: "none",
          },
          "& .MuiDataGrid-cell": {
            borderBottom: "none",
          },
          "& .name-column--cell": {
            color: colors.greenAccent[300],
          },
          "& .MuiDataGrid-columnHeaders": {
            backgroundColor: colors.blueAccent[700],
            borderBottom: "none",
          },
          "& .MuiDataGrid-virtualScroller": {
            backgroundColor: colors.primary[400],
          },
          "& .MuiDataGrid-footerContainer": {
            borderTop: "none",
            backgroundColor: colors.blueAccent[700],
          },
          "& .MuiCheckbox-root": {
            color: `${colors.greenAccent[200]} !important`,
          },
          "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
            color: `${colors.grey[100]} !important`,
          },
        }}
      >
        <DataGrid
          rows={rowData}
          columns={columns}
          components={{ Toolbar: GridToolbar }}
          getRowId={(row) => row._id}
        />
      </Box>
    </Box>
  );
};

export default Scholarships;