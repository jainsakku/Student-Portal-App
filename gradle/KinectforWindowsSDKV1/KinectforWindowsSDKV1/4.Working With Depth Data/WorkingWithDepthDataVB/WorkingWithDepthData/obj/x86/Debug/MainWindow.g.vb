﻿#ExternalChecksum("..\..\..\MainWindow.xaml","{406ea660-64cf-4c82-b6f0-42d48172a799}","42632978E063A4C802FC85477411212C")
'------------------------------------------------------------------------------
' <auto-generated>
'     This code was generated by a tool.
'     Runtime Version:4.0.30319.239
'
'     Changes to this file may cause incorrect behavior and will be lost if
'     the code is regenerated.
' </auto-generated>
'------------------------------------------------------------------------------

Option Strict Off
Option Explicit On

Imports Microsoft.Samples.Kinect.WpfViewers
Imports System
Imports System.Diagnostics
Imports System.Windows
Imports System.Windows.Automation
Imports System.Windows.Controls
Imports System.Windows.Controls.Primitives
Imports System.Windows.Data
Imports System.Windows.Documents
Imports System.Windows.Ink
Imports System.Windows.Input
Imports System.Windows.Markup
Imports System.Windows.Media
Imports System.Windows.Media.Animation
Imports System.Windows.Media.Effects
Imports System.Windows.Media.Imaging
Imports System.Windows.Media.Media3D
Imports System.Windows.Media.TextFormatting
Imports System.Windows.Navigation
Imports System.Windows.Shapes
Imports System.Windows.Shell

Namespace WorkingWithDepthData
    
    '''<summary>
    '''MainWindow
    '''</summary>
    <Microsoft.VisualBasic.CompilerServices.DesignerGenerated(),  _
     System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")>  _
    Partial Public Class MainWindow
        Inherits System.Windows.Window
        Implements System.Windows.Markup.IComponentConnector
        
        
        #ExternalSource("..\..\..\MainWindow.xaml",7)
        <System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")>  _
        Friend WithEvents image1 As System.Windows.Controls.Image
        
        #End ExternalSource
        
        
        #ExternalSource("..\..\..\MainWindow.xaml",8)
        <System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")>  _
        Friend WithEvents kinectSensorChooser1 As Microsoft.Samples.Kinect.WpfViewers.KinectSensorChooser
        
        #End ExternalSource
        
        
        #ExternalSource("..\..\..\MainWindow.xaml",9)
        <System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")>  _
        Friend WithEvents kinectDepthViewer1 As Microsoft.Samples.Kinect.WpfViewers.KinectDepthViewer
        
        #End ExternalSource
        
        Private _contentLoaded As Boolean
        
        '''<summary>
        '''InitializeComponent
        '''</summary>
        <System.Diagnostics.DebuggerNonUserCodeAttribute()>  _
        Public Sub InitializeComponent() Implements System.Windows.Markup.IComponentConnector.InitializeComponent
            If _contentLoaded Then
                Return
            End If
            _contentLoaded = true
            Dim resourceLocater As System.Uri = New System.Uri("/WorkingWithDepthData;component/mainwindow.xaml", System.UriKind.Relative)
            
            #ExternalSource("..\..\..\MainWindow.xaml",1)
            System.Windows.Application.LoadComponent(Me, resourceLocater)
            
            #End ExternalSource
        End Sub
        
        <System.Diagnostics.DebuggerNonUserCodeAttribute(),  _
         System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never),  _
         System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes"),  _
         System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity"),  _
         System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")>  _
        Sub System_Windows_Markup_IComponentConnector_Connect(ByVal connectionId As Integer, ByVal target As Object) Implements System.Windows.Markup.IComponentConnector.Connect
            If (connectionId = 1) Then
                
                #ExternalSource("..\..\..\MainWindow.xaml",4)
                AddHandler CType(target,WorkingWithDepthData.MainWindow).Loaded, New System.Windows.RoutedEventHandler(AddressOf Me.Window_Loaded)
                
                #End ExternalSource
                
                #ExternalSource("..\..\..\MainWindow.xaml",4)
                AddHandler CType(target,WorkingWithDepthData.MainWindow).Closing, New System.ComponentModel.CancelEventHandler(AddressOf Me.Window_Closing)
                
                #End ExternalSource
                Return
            End If
            If (connectionId = 2) Then
                Me.image1 = CType(target,System.Windows.Controls.Image)
                Return
            End If
            If (connectionId = 3) Then
                Me.kinectSensorChooser1 = CType(target,Microsoft.Samples.Kinect.WpfViewers.KinectSensorChooser)
                Return
            End If
            If (connectionId = 4) Then
                Me.kinectDepthViewer1 = CType(target,Microsoft.Samples.Kinect.WpfViewers.KinectDepthViewer)
                Return
            End If
            Me._contentLoaded = true
        End Sub
    End Class
End Namespace

